import React, {Component} from 'react'
import $ from 'jquery'
import env from '../enviroment';

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
            url: env.AUTH_URL,
            method: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({
                'username': this.state.username,
                'password': this.state.password
            }),
            success: (response) => {
                localStorage.setItem('accessToken', this.state.token);
                this.setState({
                    token: response.accessToken
                });
                this.props.history.push('/chat');
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
                                            type="submit" onSubmit={this.getToken}>
                                        Sign in
                                    </button>
                                </form>
                                <a href={env.APP.REGISTRATION}>Sign Up</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Login;
