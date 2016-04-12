import React from 'react';

class Login extends React.Component {
	render() {
		return (
			<div>
				<h1>Login</h1>
				<form>
					<input ref="email" type="email" placeholder="Email" />
					<input ref="password" type="password" placeholder="Password" />
					<button type="submit">Login</button>
				</form>
			</div>
		);
	}
}

export default Login;