import React from 'react';
import {Row, Input, Button, Col, Alert, Panel} from 'react-bootstrap';

class Login extends React.Component {
    render() {
        const {error} = this.props;
        return (
            <Row className="center-row">
                <Col md={4} mdOffset={8}>
                    <Panel header="SIGN IN" bsStyle="default" className="heading-text panel-default">
                        <form>
                            <Col md={12}>
                                <Input ref="email" type="text" placeholder="email"/>
                                <Input ref="password" type="password" placeholder="password" />
                            </Col>
                            <Col md={3} mdOffset={8}>
                                <Button bsStyle="success" type="submit" onClick={this.login.bind(this) }>Sign in</Button>
                            </Col>
                            <Col>
                                <p className="text-position">Need an account? <a href="/register">Sign up</a></p>
                            </Col>
                        </form>
                    </Panel>
                    {error ? <Alert bsStyle="danger"><strong>{error}</strong></Alert> : null}
                </Col>
            </Row>
        );
    }

    login(e) {
        e.preventDefault();
        const {loginUser} = this.props;
        const {email, password} = this.refs;
        loginUser(email.refs.input.value, password.refs.input.value);
        email.value = '';
        password.value = '';
    }
}

export default Login;