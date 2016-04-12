import React from 'react';
import {Row, Input, ButtonInput, Col} from 'react-bootstrap';

class Login extends React.Component {
	render() {
		return (
			<Row>
				<Col xs={6} md={4}>
					<h1>Login</h1>
					<form>
						<Input type="text" label="Text" placeholder="Enter text" />
	    				<Input type="password" label="Password" />
	    				<ButtonInput bsStyle="primary" type="submit" value="Zaloguj" />
					</form>
				</Col>
			</Row>
		);
	}
}

export default Login;