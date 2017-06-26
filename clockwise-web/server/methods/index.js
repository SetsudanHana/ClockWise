import users from './users';
import statistics from './statistics';

process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0"

export default function () {
    users();
    statistics();
}