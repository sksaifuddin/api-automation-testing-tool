import axios from 'axios';

const apiURL = 'http://csci5308vm16.research.cs.dal.ca:8080/api/get-client-db-metadata';

export async function getClientDBMetadata(url, userName, password) {
    const response = await axios.get(apiURL, {
        "databaseUrl": "jdbc:mysql://db-5308.cs.dal.ca/CSCI5308_16_TEST",
        "userName": "CSCI5308_16_TEST_USER",
        "password": "phohKaiv2b"
    });
    return response.data;
}