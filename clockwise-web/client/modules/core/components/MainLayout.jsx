import React from 'react';
import {Grid} from 'react-bootstrap';

const Layout = ({content = () => null}) => (
    <div className="wrapper">
        <Grid>
            {content() }
        </Grid>
    </div>
);

export default Layout;