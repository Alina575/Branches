#include "mpi.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;


void get_nums(char c, vector<string>& v);

int main(int argc, char **argv){
	
    vector<string> numbers;
    get_nums(' ', numbers);
	
    int N = atoi(numbers[0].c_str());
  
    double* M = new double[N * N];
    double* V = new double[N];
    for (int j = 0; j < N; j++) V[j] = 1.0/N;

    double* Result = new double[N];

    int size, rank;
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    if (rank == 0) {
		 int i = 1;
        for (int j = 0; j < N * N; j++)
            M[j] = atof(numbers[i++].c_str());

        printf("Processor %d has data: ", rank);
        for (int j = 0; j < N * N; j++)
            printf("%lf ", M[j]);
        printf("\n");
    }

    int count = 0;

    MPI_Bcast(V,count,MPI_DOUBLE,0,MPI_COMM_WORLD);
    MPI_Scatter(M, 1, MPI_DOUBLE, Result, 1, MPI_DOUBLE, 0, MPI_COMM_WORLD);

    for (int i = 0; i < N * N; i = i + N) {
        double temp = 0;
        for (int j = 0; j < N; j++) {
            temp = temp + M[i + j] * V[j];
        }
        Result[count] = temp;
        count++;
    }
    for (int j = 0; j < N; j++) {
        V[j] = Result[j];
    }

    MPI_Gather(Result, 1, MPI_DOUBLE, M, 1, MPI_DOUBLE, 0, MPI_COMM_WORLD);

    if (rank == 0) {
        cout << "Result: \n";
        for (int j = 0; j < N; j++) cout << Result[j] << "\n";

        std::sort(Result, Result + N);

        cout << "Sorted: \n";
        for (int j = N - 1; j >= 0; j--) cout << Result[j] << "\n";

    }

    MPI_Finalize();

    return 0;
}


// счтываем
void get_nums(char c, vector<string>& v){
    string s;

	ifstream file("/home/coole/matrix.txt");
    getline(file, s);
    file.close();
    string::size_type i = 0;
    string::size_type j = s.find(c);
    while (j != string::npos) {
        v.push_back(s.substr(i, j - i));
        i = ++j;
        j = s.find(c, j);
        if (j == string::npos)
            v.push_back(s.substr(i, s.length()));
    }
}