<template>
  <div>
    <b-container>
      <b-breadcrumb :items="items"></b-breadcrumb>
      <b-button @click="showModal">Registrar</b-button>
    </b-container>

    <b-col>
      <b-row>
        <b-col cols="12" md="4" lg="3" v-for="movie in movies" :key="movie.id">          
          <div>
            <b-card
            :img-src="movie.image"
            :title="movie.title"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2"
            :items="movies"
            :per-page="perPage"
            :current-page="currentPage"
            :fields="fields"
            :sort-by.sync="sortBy"
            :sort-desc.sync="sortDesc"
            label-sort-asc=""
            label-sort-desc=""
            small
          >
          <b-card-text>Description: {{ movie.description }}</b-card-text>
          <b-card-text>Director: {{ movie.director }}</b-card-text>
          <b-card-text>Publish Date: {{ movie.publishDate }}</b-card-text>
          <b-card-text>Category: {{ movie.category.name }}</b-card-text>
          <b-card-text>Status: {{ movie.status }}</b-card-text>
          <b-button @click="changeStatus(movie)">Change Status</b-button>

        </b-card>
          </div>
          <div v-if="movies.length < 0"> 
            <p>No hay películas disponibles.</p>
          </div>
        </b-col>
        <div class="overflow-auto">
          <b-pagination
            v-model="currentPage"
            :total-rows="rows"
            :per-page="perPage"
            aria-controls="my-table"
          ></b-pagination>
          <p class="mt-3">Current Page: {{ currentPage }}</p>
        </div>
      </b-row>
    </b-col>

    <b-modal
      v-model="modalVisible"
      id="modal-prevent-closing"
      title="Add Movie"
      size="lg"
      @ok="handleSubmit"
    >
      <form ref="form" @submit.stop.prevent="handleSubmit">
        <b-col>
          <b-row cols-lg="3">
            <b-form-group
              label="Title"
              label-for="title"
              invalid-feedback="Title is required"
              :state="titleState"
            >
              <b-form-input
                id="title"
                v-model="title"
                :state="titleState"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group
              label="Description"
              label-for="description"
              invalid-feedback="Description is required"
              :state="descriptionState"
            >
              <b-form-input
                id="description"
                v-model="description"
                :state="descriptionState"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group
              label="Director"
              label-for="director"
              invalid-feedback="Director is requiered"
              :state="directorState"
            >
              <b-form-input
                id="director"
                v-model="director"
                :state="directorState"
                required
              ></b-form-input>
            </b-form-group>
          </b-row>

          <b-row cols-lg="2">
            <b-form-group
              label="Date"
              label-fro="fecha"
              invalid-feedback="date is required"
              :state="fechaState"
            >
              <b-form-datepicker
                id="fecha"
                v-model="fecha"
                :state="fechaState"
              ></b-form-datepicker>
            </b-form-group>

            <b-form-group
              label="Image"
              label-for="image"
              invalid-feedback="Image is required"
              :state="imageState"
            >
              <input
                type="file"
                @change="handleFileUpload"
                accept="img"
                :state="imageState"
                required
              />
            </b-form-group>

            <b-form-group
              label="Category"
              label-for="category"
              invalid-feedback="Category is required"
              :state="categoryState"
            >
              <b-form-select v-model="category" :options="optionsCategory">
                <div class="mt-3">
                  Selected: <strong>{{ category }}</strong>
                </div>
              </b-form-select>
            </b-form-group>
          </b-row>
        </b-col>
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
      image: "",
      category: null,
      optionsCategory: [
        { text: "Select One", value: null },
        { value: 1, text: "Terror" },
        { text: "Comedia", value: 4 },
        { text: "Animacion", value: 5 },
      ],
      titleState: null,
      imageState: null,
      descriptionState: null,
      directorState: null,
      fechaState: null,
      categoryState: null,
      status: 1,
      movies: [],
      fields: [
        { key: "title", label: "description", sortable: true },
        { key: "director", label: "publishDate", sortable: true },
        { key: "image", label: "category", sortable: true },
      ],
    };
  },
  mounted() {
    this.handleSubmit();
    this.getMovie();
    this.changeStatus();
  },
  methods: {
    async getMovie() {
      try {
        const response = await PeliculaService.getMovie();
        console.log("RESPUESTA:", response);

        this.movies = response.data.content;
        console.log("DATA:", this.movies);

        if (this.movies.length === 0) {
          console.log("No hay películas disponibles.");
        } else {
          console.error("EXITO");
        }
      } catch (error) {
        console.log("ERROR GET:", error);
      }
    },

    checkFormValidity() {
      const valid = this.$refs.form.checkValidity();
      this.titleState = valid;
      this.imageState = valid;
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
        image: this.image,
        category: { id: this.category },
      };
      console.log("Movie Data:", movieData);
      try {
        const data = await PeliculaService.saveMovie(movieData);
        console.log("Registro exitoso!", data);
      } catch (error) {
        console.error("Error al registrar la película:", error);
      }
    },
    async changeStatus (movie) {
      movie.status = !movie.status;
      try {
        await PeliculaService.changeStatus(movie.id, movie.status);
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
      this.image = "";
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
    handleFileUpload(event) {
      const file = event.target.files[0];
      this.image = URL.createObjectURL(file);
    },
  },
};
</script>

<style></style>