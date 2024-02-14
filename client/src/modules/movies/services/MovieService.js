import axios from "axios";
const api = "http://localhost:8080/api/movie/"

const saveMovie = async (movieData) => {
    try {
        const response = await axios.post(api, movieData, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response;
    } catch (error) {
        console.error("Error al enviar la solicitud:", error);
        if (error.response) {
            console.error("Respuesta del servidor:", error.response.data);
        }
        throw error;
    }
};

const updateMovie = async (movieData) => {
    try {
        const response = await axios.put(api, movieData, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response;
    } catch (error) {
        console.error("Error al enviar la solicitud:", error);
        if (error.response) {
            console.error("Respuesta del servidor:", error.response.data);
        }
        throw error;
    }
};

const getMovies = async (params) => {
    try {
        const pagination = params ? `?page=${params?.page || 0}&size=${params?.size || 10}&sort=${params?.sort || "id"}&direction=${params?.direction || "asc"}` : '';
        const response = await axios.post(`${api}paged/${pagination}`, {});
        return response.data;
    } catch (error) {
        throw error;
    }
};

const getMovie = async (movieId) => {
    try {
        const response = await axios.get(api + movieId)
        return response.data;
    } catch (error) {
        console.log("Obtener uno fallo!", error);
        throw error;
    }
}

const changeStatus = async (movieId) => {
    try {
        const response = await axios.patch(api + movieId)
        return response.data;
    } catch (error) {
        console.log("Change status fallo!", error);
        throw error;
    }
}

export default {
    getMovies,
    getMovie,
    saveMovie,
    updateMovie,
    changeStatus
}