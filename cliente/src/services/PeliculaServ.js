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

export default {
    saveMovie,
}
