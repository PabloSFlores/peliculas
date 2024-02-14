<template>
  <div class="mt-5">
    <!-- buscador -->
    <b-row>
      <b-col cols="8">
        <b-form-input placeholder="Buscador..." :type="'search'" />
        <b-form-invalid-feedback tooltip> </b-form-invalid-feedback>
      </b-col>
      <b-col cols="4">
        <b-button>Buscar</b-button>
      </b-col>
    </b-row>
    <b-row class="d-flex flex-row-reverse">
      <b-button>+</b-button>
    </b-row>
    <!-- listato -->
    <b-row v-if="movies.length > 0">
      <b-col cols="12">
        <b-row>
          <b-col
            cols="12"
            sm="6"
            md="4"
            lg="4"
            v-for="movie in movies"
            :key="movie.id"
          >
            <b-card
              :title="movie.title"
              img-src="https://picsum.photos/600/300/?image=25"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem"
              class="mb-2"
            >
              <b-card-text>Description: {{ movie.description }}</b-card-text>
              <b-card-text>Director: {{ movie.director }}</b-card-text>
              <b-card-text>Publish Date: {{ movie.publishDate }}</b-card-text>
              <b-card-text>Category: {{ movie.category.name }}</b-card-text>
              <b-card-text
                >Status:
                {{ movie.status ? "Habilitado" : "Deshabilitado" }}</b-card-text
              >
              <b-row class="d-flex flex-row-reverse">
                <b-button @click="changeStatus(movie)" class="ml-2">
                  {{ movie.status ? "Deshabilitar" : "Habilitar" }}</b-button
                >
                <b-button @click="changeStatus(movie)"> Actualizar </b-button>
              </b-row>
            </b-card>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
    <!-- mensaje de no registros -->
    <b-row v-else>
      <b-col cols="12" class="mt-5">
        <b-card class="text-center">
          <div>No hay registros disponibles</div>
        </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import MovieService from "../services/MovieService";
import CategoryService from "../../category/services/CategoryService";

export default {
  data() {
    return {
      movies: [],
      movieData: {
        id: null,
        title: null,
        description: null,
        director: null,
        image: null,
        publishDate: null,
        status: null,
        category: {
          id: null,
          name: null,
        },
      },
      categoriesOptions: [
        {
          value: null,
          text: "Selecciona una opció",
        },
      ],
    };
  },
  methods: {
    async getMovies() {
      try {
        const { data: res } = await MovieService.getMovies(null);
        this.movies = res.content;
      } catch (error) {
        console.error(error.message);
      }
    },
    async getCategories() {
      try {
        const {
          data: { content: res },
        } = await CategoryService.getCategories(null);
        const newCategoriesOptions = res.map((c) => ({
          value: c.id,
          text: c.name,
        }));
        this.categoriesOptions.push(newCategoriesOptions);
        console.log(this.categoriesOptions);
      } catch (error) {
        console.error(error.message);
      }
    },
  },
  mounted() {
    this.getMovies();
    this.getCategories();
  },
};
</script>

<style>
</style>