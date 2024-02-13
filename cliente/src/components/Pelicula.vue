<template>
  <div>
    <b-breadcrumb :items="items"></b-breadcrumb>
    <b-col>
      <b-row>
        <div class="text-end" cols="12">
          <b-button @click="showModal">Registrar</b-button>
        </div>
        <b-col cols="12" sm="6" md="4" lg="4" v-for="movie in movies" :key="movie.id">
          <div>
            <b-card :img-src="movie.image" :title="movie.title" img-alt="Image" img-top tag="article" class="mb-2">
              <b-card-text>Description: {{ movie.description }}</b-card-text>
              <b-card-text>Director: {{ movie.director }}</b-card-text>
              <b-card-text>Publish Date: {{ movie.publishDate }}</b-card-text>
              <b-card-text>Category: {{ movie.category.name }}</b-card-text>
              <b-card-text>Status: {{ movie.status ? "Habilitado" : "Deshabilitado" }}</b-card-text>
              <b-button @click="changeStatus(movie)"> {{ movie.status ? "Deshabilitar" : "Habilitar" }}</b-button>
              <b-button @click="changeStatus(movie)" class="ms-2"> Actualizar </b-button>
            </b-card>
          </div>
          <div v-if="movies.length <= 0">
            <p>No hay películas disponibles.</p>
          </div>
        </b-col>
        <div class="overflow-auto">
          <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage"
            aria-controls="my-table"></b-pagination>
        </div>
      </b-row>
    </b-col>

    <!-- Modal de registro -->
    <b-modal v-model="modalVisible" id="modal-prevent-closing" title="Add Movie" size="lg" @ok="handleSubmit">
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-row>
          <b-col cols="12" class="my-2">
            <b-form-group label="Título" label-for="title" invalid-feedback="Title is required" :state="titleState">
              <b-form-input id="title" v-model="title" :state="titleState" required></b-form-input>
            </b-form-group>
          </b-col>
          <b-col cols="12" class="mb-2">
            <b-form-group label="Descripción" label-for="description" invalid-feedback="Description is required"
              :state="descriptionState">
              <b-form-input id="description" v-model="description" :state="descriptionState" required></b-form-input>
            </b-form-group>
          </b-col>
          <b-col cols="12" class="mb-2">
            <b-form-group label="Director" label-for="director" invalid-feedback="Director is requiered"
              :state="directorState">
              <b-form-input id="director" v-model="director" :state="directorState" required></b-form-input>
            </b-form-group>
          </b-col>
          <b-col cols="12" class="mb-2">
            <b-form-group label="Fecha de publicación" label-fro="fecha" invalid-feedback="date is required"
              :state="fechaState">
              <b-form-datepicker id="fecha" v-model="fecha" :state="fechaState"></b-form-datepicker>
            </b-form-group>
          </b-col>
          <b-col cols="12" class="mb-2">
            <b-form-group label="Categoría" label-for="category" invalid-feedback="Category is required"
              :state="categoryState">
              <b-form-select v-model="category" :options="optionsCategory">
                <div class="mt-3">
                  Selected: <strong>{{ category }}</strong>
                </div>
              </b-form-select>
            </b-form-group>
          </b-col>
        </b-row>
      </form>
    </b-modal>
  </div>
</template>

<script>
import PeliculaService from "../services/PeliculaServ";

export default {
  data() {
    return {
      items: [
        {
          text: "Inicio",
          to: { name: "inicio" },
        },
        {
          text: "Pelicula",
          active: true,
        },
      ],
      modalVisible: false,
      filtro: null,
      sortBy: "title",
      sortDesc: false,
      perPage: 5,
      rows: 0,
      currentPage: 5,
      isUploading: false,
      title: "",
      description: "",
      director: "",
      fecha: "",
      category: null,
      optionsCategory: [
        { text: "Selecciona una categoría", value: null },
        { value: 1, text: "Terror" },
        { text: "Comedia", value: 4 },
        { text: "Animacion", value: 5 },
      ],
      titleState: null,
      descriptionState: null,
      directorState: null,
      fechaState: null,
      categoryState: null,
      status: 1,
      movies: [],
    };
  },
  mounted() {
    this.getMovie();
  },
  methods: {
    async getMovie() {
      try {
        const { data: response } = await PeliculaService.getMovie();
        this.movies = response.content;
        
        if (this.movies.length === 0) {
          console.log("No hay películas disponibles.");
        } else {
          console.log("EXITO");
        }
      } catch (error) {
        console.log("ERROR GET:", error);
      }
    },

    checkFormValidity() {
      const valid = this.$refs.form.checkValidity();
      this.titleState = valid;
      this.descriptionState = valid;
      this.categoryState = valid;
      return valid;
    },
    showModal() {
      this.modalVisible = true;
    },
    async handleSubmit() {
      // Exit when the form isn't valid
      if (!this.checkFormValidity()) {
        return;
      }
      const movieData = {
        title: this.title,
        description: this.description,
        status: this.status,
        director: this.director,
        publishDate: this.fecha,
        category: { id: this.category },
      };
      console.log("Movie Data:", movieData);
      try {
        const data = await PeliculaService.saveMovie(movieData);
        console.log("Registro exitoso!", data);
        this.getMovie();
      } catch (error) {
        console.error("Error al registrar la película:", error);
      }
    },
    async changeStatus(movie) {
      try {
        const { data: response } = await PeliculaService.changeStatus(movie.id);
        if (!response.error) this.getMovie();
      } catch (error) {
        movie.status = !movie.status;
      }
    },
    resetForm() {
      // Hide the modal manually
      this.modalVisible = false;
      // Reset form fields
      this.title = "";
      this.description = "";
      this.director = "";
      this.fecha = "";
      this.category = null;
      // Reset form validation states
      this.titleState = null;
      this.imageState = null;
      this.descriptionState = null;
      this.directorState = null;
      this.fechaState = null;
      this.categoryState = null;
      this.$refs.form.reset();
    },
  },
};
</script>

<style></style>