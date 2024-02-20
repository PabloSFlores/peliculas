<template>
  <div class="mt-5">
    <!-- buscador -->
    <b-row>
      <b-col cols="12">
        <h1>Realiza una búsqueda</h1>
      </b-col>
      <b-col cols="6">
        <b-form-group label="Título de la película:" label-for="title">
          <b-form-input id="title" v-model="filters.title" placeholder="Título de la película..." :type="'search'" />
        </b-form-group>
      </b-col>
      <b-col cols="6">
        <b-form-group label="Director:" label-for="director">
          <b-form-input v-model="filters.director" placeholder="Director..." :type="'search'" />
        </b-form-group>
      </b-col>
      <b-col cols="6">
        <b-form-group label="Fecha 1:" label-for="fecha1">
          <b-form-datepicker id="fecha1" v-model="filters.firstDate"></b-form-datepicker>
        </b-form-group>
      </b-col>
      <b-col cols="6">
        <b-form-group label="Fecha 2:" label-for="fecha2">
          <b-form-datepicker id="fecha2" v-model="filters.secondDate"></b-form-datepicker>
        </b-form-group>
      </b-col>
      <b-col cols="12">
        <b-form-group label="Categorías:" label-for="categories">
          <b-form-select id="categories" v-model="filters.categoryId" :options="categoriesOptions">
            <template #first>
              <b-form-select-option :value="null">Todas las categoríás</b-form-select-option>
            </template>
          </b-form-select>
        </b-form-group>
      </b-col>
      <b-col cols="8">
        <b-form-group label="Ordenar por:" label-for="sorts">
          <b-form-select id="sorts" v-model="customOrder.sort" :options="sortOptions" />
        </b-form-group>
      </b-col>
      <b-col cols="4">
        <b-form-group label="Dirección:" label-for="directions">
          <b-form-select id="directions" v-model="customOrder.direction" :options="directionOptions" />
        </b-form-group>
      </b-col>
      <b-col cols="12" class="mt-3">
        <b-row class="d-flex flex-row-reverse">
          <b-button @click="searchMovies" variant="success" class="mr-3 my-2">
            Buscar
          </b-button>
        </b-row>
      </b-col>
    </b-row>
    <b-row class="d-flex flex-row-reverse">
      <b-button @click="addMovie" variant="success" class="mr-3 my-2">
        <h4>+</h4>
      </b-button>
    </b-row>
    <!-- listato -->
    <b-row v-if="movies.length > 0">
      <b-col cols="12">
        <b-row>
          <b-col cols="12" sm="6" md="4" lg="4" v-for="movie in movies" :key="movie.id">
            <b-card :title="movie.title" img-src="https://picsum.photos/600/300/?image=25" img-alt="Image" img-top
              tag="article" style="max-width: 20rem" class="mb-2">
              <b-card-text>Description: {{ movie.description }}</b-card-text>
              <b-card-text>Director: {{ movie.director }}</b-card-text>
              <b-card-text>Publish Date: {{ movie.publishDate }}</b-card-text>
              <b-card-text>Category: {{ movie.category.name }}</b-card-text>
              <b-card-text>Status:
                {{ movie.status ? "Habilitado" : "Deshabilitado" }}</b-card-text>
              <b-row class="d-flex flex-row-reverse">
                <b-button @click="changeStatus(movie)" class="ml-2">
                  {{ movie.status ? "Deshabilitar" : "Habilitar" }}</b-button>
                <b-button @click="updateMovie(movie)"> Actualizar </b-button>
              </b-row>
            </b-card>
          </b-col>
        </b-row>
      </b-col>
      <b-col cols="12">
        <div class="overflow-auto">
          <b-pagination v-model="pagination.page" :total-rows="rows" :per-page="pagination.size"
            @change="handlePageChange"></b-pagination>
        </div>
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

    <!-- Incluir el componente modal -->
    <ModalForm :movieData="movieData" :isNew="isNewMovie" @getMovies="getMovies" />

    <!-- Loading -->
    <loading :active.sync="isLoading" :can-cancel="false" :is-full-page="fullPage"></loading>
  </div>
</template>

<script>
import MovieService from "../services/MovieService";
import CategoryService from "../../category/services/CategoryService";
import ModalForm from './ModalForm.vue';

import Loading from 'vue-loading-overlay';
import 'vue-loading-overlay/dist/vue-loading.css';

export default {
  components: {
    ModalForm,
    Loading
  },
  data() {
    return {
      pagination: {
        page: 1,
        size: 6,
        sort: "id",
        direction: "asc"
      },
      filters: {
        title: "",
        director: "",
        categoryId: null,
        firstDate: null,
        secondDate: null
      },
      customOrder: {
        sort: "id",
        direction: "asc"
      },
      categoriesOptions: [],
      sortOptions: [
        { value: "id", text: "Normal" },
        { value: "publishDate", text: "Fecha" },
        { value: "title", text: "Título" },
      ],
      directionOptions: [
        { value: "asc", text: "Ascendente" },
        { value: "desc", text: "Descendente" },
      ],
      rows: null,
      isLoading: false,
      fullPage: true,
      movies: [],
      isNewMovie: true,
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
    };
  },
  methods: {
    addMovie() {
      this.$bvModal.show("form");
      this.isNewMovie = true;
      this.resetMovieData();
    },
    async updateMovie(movie) {
      this.$bvModal.show("form");
      this.isModalOpen = true;
      this.isNewMovie = false;
      const { data: selectedMovie } = await MovieService.getMovie(movie.id);
      this.movieData = { ...selectedMovie }
    },
    resetMovieData() {
      this.movieData = {
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
      };
    },
    async changeStatus(movie) {
      this.isLoading = true;
      try {
        const { error: statusUpdated } = await MovieService.changeStatus(movie.id);
        if (!statusUpdated) this.getMovies();
      } catch (error) {
        console.error(error);
      } finally {
        this.isLoading = false;
      }
    },
    async getMovies() {
      this.isLoading = true;
      try {
        const { data: res } = await MovieService.getMovies({
          page: this.pagination.page - 1,
          size: this.pagination.size,
          sort: this.pagination.sort,
          direction: this.pagination.direction
        });
        this.movies = res.content;
        this.rows = res.totalElements;
      } catch (error) {
        console.error(error.message);
      } finally {
        this.isLoading = false;
      }
    },
    async getCategories() {
      try {
        const { data: { content: res } } = await CategoryService.getCategories(null);
        this.categoriesOptions = res.map(c => ({
          value: c.id,
          text: c.name,
        }));
      } catch (error) {
        console.error(error.message);
      }
    },
    async searchMovies() {
      console.log(this.filters);
      console.log(this.customOrder);
      this.isLoading = true;
      try {
        const { data: res } = await MovieService.getMovies({
          page: 0,
          size: this.pagination.size,
          sort: this.customOrder.sort,
          direction: this.customOrder.direction
        }, this.filters);
        this.movies = res.content;
        this.rows = res.totalElements;
      } catch (error) {
        console.error(error.message);
      } finally {
        this.isLoading = false;
      }
    },
    handlePageChange(newPage) {
      this.pagination.page = newPage;
      this.getMovies();
    }
  },
  mounted() {
    this.getMovies();
    this.getCategories();
  },
};
</script>

<style></style>