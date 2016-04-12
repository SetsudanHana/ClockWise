import {createApp} from 'mantra-core';
import initContext from './configs/context';

import coreModule from './modules/core';
import usersModule from './modules/users';

const context = initContext();

const app = createApp(context);
app.loadModule(coreModule);
app.loadModule(usersModule);
app.init();