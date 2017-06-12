var Spinner = React.createClass({
    propTypes: {
        message: React.PropTypes.string
    },

    getDefaultProps: function () {
        return {
            message: 'Loading...'
        }
    },

    render: function () {
        return <div>{this.props.message}</div>;
    }
});

var CreateBox = React.createClass({
    getInitialState: function (e) {
        return {
            username: '',
            email: '',
            phone: '',
            createMessage: '',
            isFetchingCreateMessage: false,
            users: ''
        };
    },

    handleFormSubmit: function (e) {
        e.preventDefault();
        this.setState({
            createMessage: '',
            isFetchingCreateMessage: true
        });

        //FIXME: check for errors
        //FIXME: set accept header
        fetch('/api/create?username=' + encodeURIComponent(this.state.username) +
            '&email=' + encodeURIComponent(this.state.email) +
            '&phone=' + encodeURIComponent(this.state.phone))
            .then(response => response.text())
            .then(text => this.setState({
                isFetchingCreateMessage: false,
                createMessage: text
            }));

        /*
         method: 'POST',
         headers: new Headers({
         'Content-Type': 'text/plain'
         }),
         body: JSON.stringify({
         username: document.getElementById('usernameInput').value,
         email: document.getElementById('emailInput').value,
         phone: document.getElementById('phoneInput').value,
         })
         })
         body: 'username=' + encodeURIComponent(this.state.username) +
         '&email=' + encodeURIComponent(this.state.email) +
         '&phone=' + encodeURIComponent(this.state.phone)
         */
    },

    handleUsernameChange: function (e) {
        this.setState({username: e.target.value});
    },

    handleEmailChange: function (e) {
        this.setState({email: e.target.value});
    },

    handlePhoneChange: function (e) {
        this.setState({phone: e.target.value});
    },

    componentDidMount: function () {
        this.getUsers();
    },

    getUsers: function () {
        $.ajax({url: "/api/create"}).success(function (res) {
            this.setState({users: res});
        }.bind(this));
    },

    //TODO: replace with a nice looking spinner
    //React.createElement('div', {clssName: 'content'}, this.props.messages)
    //return <div className="content"
    render: function () {
        return (
            <div>
                <div className="createBox">
                    <form onSubmit={this.handleFormSubmit}>
                        <label htmlFor="usernameInput">Name</label>
                        <input id="usernameInput" type="text" name="username"
                               onChange={this.handleUsernameChange}
                               value={this.state.username}/>

                        <label htmlFor="emailInput">E-mail</label>
                        <input id="emailInput" type="text" name="email"
                               onChange={this.handleEmailChange}
                               value={this.state.email}/>

                        <label htmlFor="phoneInput">Phone</label>
                        <input id="phoneInput" type="text" name="phone"
                               onChange={this.handlePhoneChange}
                               value={this.state.phone}/>

                        <button type="submit" disabled={this.state.isFetchingHelloMessage}>
                            Create
                        </button>
                    </form>

                    <div>
                        {this.state.isFetchingCreateMessage ?
                            <Spinner message="Loading..."/> :
                            this.state.createMessage}
                    </div>
                    <div>
                        <div>Users: {this.state.users}</div>

                    </div>
                </div>

                <div className="usersBox">

                </div>

            </div>
        );
    }
});
ReactDOM.render(
    <CreateBox/>,
    document.getElementById('content')
);