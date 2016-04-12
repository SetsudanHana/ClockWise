import React from 'react';
import {Grid} from 'react-bootstrap';

const Layout = ({content = () => null}) => (
	<Grid>
		{content()}
	</Grid>
);

export default Layout;