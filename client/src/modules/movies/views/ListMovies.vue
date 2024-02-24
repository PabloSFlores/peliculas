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

    <!-- Botón para agregar -->
    <b-row class="d-flex flex-row-reverse">
      <b-button @click="addMovie" variant="success" class="mr-3 my-2">
        <h4>+</h4>
      </b-button>
    </b-row>

    <!-- Formulario -->
    <b-row class="mb-5" style="display: flex; justify-content: center; align-items: center;" v-show="showElement">
      <b-card draggable>
        <form @submit.prevent="handleSubmit">
          <b-row>
            <b-col cols="12">
              <h1>Realiza una búsqueda</h1>
            </b-col>
            <b-col cols="4" class="my-2">
              <b-form-group label="Título" label-for="title">
                <b-form-input id="title" v-model="movieForm.title" required></b-form-input>
              </b-form-group>
            </b-col>
            <b-col cols="4" class="mb-2">
              <b-form-group label="Descripción" label-for="description">
                <b-form-input id="description" v-model="movieForm.description" required></b-form-input>
              </b-form-group>
            </b-col>
            <b-col cols="4" class="mb-2">
              <b-form-group label="Director" label-for="director">
                <b-form-input id="director" v-model="movieForm.director" required></b-form-input>
              </b-form-group>
            </b-col>
            <b-col cols="6">
              <b-form-group label="Fecha de publicación" label-fro="fecha">
                <b-form-datepicker id="fecha" v-model="movieForm.publishDate"></b-form-datepicker>
              </b-form-group>
            </b-col>
            <b-col cols="6">
              <b-form-group label="Categoría" label-for="category">
                <b-form-select v-model="movieForm.category.id" :options="categoriesOptions">
                  <template #first>
                    <b-form-select-option :value="null">Selecciona una opción</b-form-select-option>
                  </template>
                </b-form-select>
              </b-form-group>
            </b-col>
            <b-col cols="12">
              <b-row class="d-flex flex-row-reverse">
                <b-button type="submit" variant="success" class="mx-3">Registrar</b-button>
              </b-row>
            </b-col>
          </b-row>
        </form>
      </b-card>
    </b-row>

    <!-- listato -->
    <b-row v-if="movies.length > 0" class="drop-zone mx-5 p-5" @drop="saveMovie()" @dragover.prevent @dragenter.prevent>
      <b-col cols="12">
        <b-row>
          <TransitionGroup name="zoom" tag="div" class="d-flex flex-row flex-wrap">
            <b-col v-for="movie in movies" :key="movie.id">
              <b-card :title="movie.title" :img-src="'https://picsum.photos/600/300/?image=' + movie.id" img-alt="Image"
                img-top tag="article" style="max-width: 20rem; min-width: 20rem;" class="mb-2">
                <b-card-text>Description: {{ movie.description }}</b-card-text>
                <b-card-text>Director: {{ movie.director }}</b-card-text>
                <b-card-text>Publish Date: {{ movie.publishDate }}</b-card-text>
                <b-card-text>Category: {{ movie.category.name }}</b-card-text>
                <b-card-text>Status: {{ movie.status ? 'Habilitado' : 'Deshabilitado' }}</b-card-text>
                <b-row class="d-flex flex-row-reverse">
                  <b-button @click="changeStatus(movie)" class="ml-2">{{ movie.status ? 'Deshabilitar' : 'Habilitar'
                  }}</b-button>
                  <b-button @click="updateMovie(movie)">Actualizar</b-button>
                </b-row>
              </b-card>
            </b-col>
          </TransitionGroup>
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
    <b-row v-else class="drop-zone" @drop="saveMovie()" @dragover.prevent @dragenter.prevent>
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
      showElement: true,
      pagination: {
        page: 1,
        size: 10,
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
      movieForm: {
        id: null,
        title: null,
        description: null,
        director: null,
        image: null,
        publishDate: null,
        status: true,
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
        }, this.filters);
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
      this.isLoading = true;
      try {
        this.pagination.page = 0;
        const { data: res } = await MovieService.getMovies({
          page: this.pagination.page,
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
    },
    async saveMovie() {
      this.isLoading = true;
      try {
        await MovieService.saveMovie(this.movieForm);
        this.isLoading = false;
      } catch (error) {
        console.error(error);
      } finally {
        this.getMovies();
      }
    },
    onScroll() {
      // Obtiene la posición actual del scroll
      const currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop;
      console.log(currentScrollPosition);

      // La función abs para tener valores absolutos y se delimita con un offset o bien llamado 
      // margen para que el valor de la posición sea después de desplazarce y no desde que uno se desplaza
      if (Math.abs(currentScrollPosition - this.lastScrollPosition) < 60) {
        return;
      }
      // aqui determinamos si la posición es mayor a la posición anterior. Entonces, si lo es, mostramos el elemento.
      this.showElement = currentScrollPosition < this.lastScrollPosition;
      //  
      this.lastScrollPosition = currentScrollPosition;
    },
  },
  mounted() {
    this.getMovies();
    this.getCategories();
    window.addEventListener("scroll", this.onScroll);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.onScroll);
  },
};
</script>

<style>
.zoom-enter-active,
.zoom-leave-active {
  transition: transform 0.5s;
}

.zoom-enter,
.zoom-leave-to {
  transform: scale(0);
}

.drop-zone {
  background-color: #eee;
  margin-bottom: 10px;
  padding: 10px;
}
</style>