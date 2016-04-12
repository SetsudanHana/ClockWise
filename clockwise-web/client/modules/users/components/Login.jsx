import React from 'react';
import {Row, Input, Button, Col, Alert} from 'react-bootstrap';

class Login extends React.Component {
	render() {
		const {error} = this.props;
		console.log(error);
		return (
			<Row>
				<Col xs={6} md={4}>
					<h1>Login</h1>
					<form>
						<Input ref="login" type="text" label="login" />
	    				<Input ref="password" type="password" label="hasło" />
	    				<Button bsStyle="primary" type="submit" onClick={this.login.bind(this)}>Zaloguj</Button>
					</form>
					{error ? <Alert bsStyle="warning"><strong>{error}</strong></Alert> : null}
				</Col>
			</Row>
		);
	}

	login(e) {
		e.preventDefault();
		const {loginUser} = this.props;
		const {login, password} = this.refs;
		loginUser(login.value, password.value);
		login.value = '';
		password.value = '';
	}
}

export default Login;