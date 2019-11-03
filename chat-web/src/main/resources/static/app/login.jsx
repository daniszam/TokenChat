import React, {Component} from 'react'
import ReactDom from "react-dom";
import $ from 'jquery'

class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            token: null,
            username: '',
            password: '',
        };
        this.changeUsername = this.changeUsername.bind(this);
        this.changePassword = this.changePassword.bind(this);
        this.getToken = this.getToken.bind(this);
    }

    changeUsername(event) {
        this.setState({username: event.target.value});
    }

    changePassword(event) {
        this.setState({password: event.target.value});
    }

    getToken() {
        $.ajax({
            url: "http://localhost:8080/auth",
            method: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({
                'username': this.state.username,
                'password': this.state.password
            }),
            success: (response) => {
                this.setState({
                    token: response.accessToken
                });
            },
            error: (response) => {
                this.setState({
                    token: 'Incorrect cred'
                });
            }
        });
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-sm-9 col-md-7 col-lg-5 mx-auto">
                        <div className="card card-signin my-5">
                            <div className="card-body">
                                <h5 className="card-title text-center">Sign In</h5>
                                <form className="form-signin">
                                    <div className="form-label-group">
                                        <input type="text" id="inputEmail" className="form-control"
                                               placeholder="username" required autoFocus value={this.state.username}
                                               onChange={this.changeUsername}/>
                                        <label htmlFor="inputEmail">Username</label>
                                    </div>

                                    <div className="form-label-group">
                                        <input type="password" id="inputPassword" className="form-control"
                                               placeholder="Password" required value={this.state.password}
                                               onChange={this.changePassword}/>
                                        <label htmlFor="inputPassword">Password</label>
                                    </div>
                                    <button className="btn btn-lg btn-primary btn-block text-uppercase"
                                            type="button" onClick={this.getToken}>Sign in
                                    </button>
                                </form>
                                <h1>Token: {this.state.token}</h1>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

ReactDom.render(
    <Login/>,
    document.getElementById('react')
);
