import axios from "axios";

let api = "http://localhost:8080/api/movie/";

const saveMovie = async (movieData) => {
  try {
    const response = await axios.post(api, movieData, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    console.log("Registro exitoso!", response);
    return response;
  } catch (error) {
    console.error("Error al enviar la solicitud:", error);
    if (error.response) {
      console.error("Respuesta del servidor:", error.response.data);
    }
    throw error;
  }
};

const getMovie = async () => {
  try {
    const response = await axios.post(api + "paged/");
    console.log("Get exitoso!", response);
    return response.data;
  } catch (error) {
    throw error;
  }
};

const changeStatus = async (movieId) => {
  try {
    const response = await axios.patch(api + movieId)
    console.log("Change status!", response);
    return response.data;
  } catch (error) {
    console.log("Change status fallo!", error);
    throw error;
  }
}

export default {
  saveMovie,
  getMovie,
  changeStatus
};
