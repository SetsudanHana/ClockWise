import React from 'react';
import {Row, Input, Button, Col, Alert, Panel} from 'react-bootstrap';

class Register extends React.Component {
    render() {
        const {error} = this.props;
        return (
            <Row className="center-row">
                <Col md={6} mdOffset={3}>
                    <Panel header="SIGN UP" bsStyle="default" className="heading-text panel-default">
                        <form>
                            <Col md={12}>
                                <Input ref="username" type="text" placeholder="Username" />
                                <Input ref="email" type="text" placeholder="email"/>
                                <Input ref="password" type="password" placeholder="password" />
                                <Input ref="confirmPassword" type="password" placeholder="confirm password" />
                            </Col>
                            <Col md={5}>
                                <Button bsStyle="success" block type="submit" onClick={this.register.bind(this) }>Sign up</Button>
                            </Col>
                            <Col md={5} mdOffset={2}>
                                <Button bsStyle="default" block type="button">Cancel</Button>
                            </Col>
                        </form>
                    </Panel>
                    {error ? <Alert bsStyle="danger"><strong>{error}</strong></Alert> : null}
                </Col>
            </Row>
        );
    }
    
    register(e) {
        e.preventDefault();
        const {registerUser} = this.props;
        const {username, email, password, confirmPassword} = this.refs;
        registerUser(username.refs.input.value, email.refs.input.value, 
            password.refs.input.value, confirmPassword.refs.input.value);
        username.value = '';
        email.value = '';
        password.value = '';
        confirmPassword.value = '';
    }
}

export default Register;