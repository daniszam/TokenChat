import React, {Component} from 'react'
import env from '../enviroment';
import $ from 'jquery'


class SignUp extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: ''
        };
        this.changePassword = this.changePassword.bind(this);
        this.changeUsername = this.changeUsername.bind(this);
    }

    changePassword(event) {
        this.setState({password: event.target.value})
    }

    changeUsername(event) {
        this.setState({username: event.target.value})
    }

    signUp() {
        $.ajax({
            url: env.SIGN_UP_URL,
            method: 'POST',
            contentType: env.APPLICATION_JSON,
            dataType: 'json',
            data: JSON.stringify({
                username: this.state.username,
                password: this.state.password,
            }),
            success: (response) => {
                this.props.history.push(env.APP.LOGIN)
            },
            error: (response) => {

            }
        })
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-sm-9 col-md-7 col-lg-5 mx-auto">
                        <div className="card card-signin my-5">
                            <div className="card-body">
                                <h5 className="card-title text-center">Sign Up</h5>
                                <form>
                                    <div className="col-md-12 mb-3">
                                        <label htmlFor="validationServerUsername">Username</label>
                                        <div className="input-group">
                                            <div className="input-group-prepend">
                                                <span className="input-group-text" id="inputGroupPrepend3">@</span>
                                            </div>
                                            <input type="text" className="form-control is-invalid"
                                                   id="validationServerUsername"
                                                   placeholder="Username" aria-describedby="inputGroupPrepend3"
                                                   value={this.state.username} onChange={this.changeUsername}
                                                   required/>
                                            <div className="invalid-feedback">
                                                Please choose a username.
                                            </div>
                                            <input type="password" className="form-control" id="password"
                                                   placeholder="Password" name="up" value={this.state.password}
                                                   onChange={this.changePassword}/>

                                            <input type="password" className="form-control" id="password"
                                                   placeholder="Password" name="up2"/>
                                        </div>
                                        <br/>
                                        <button className="btn btn-primary" type="submit" onSubmit={this.signUp}>Sign
                                            up
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default SignUp