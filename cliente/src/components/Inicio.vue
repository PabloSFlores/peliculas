<template>
  <div>
    <b-container>
      <b-breadcrumb :items="items"></b-breadcrumb>
      <b-button variant="outline-primary" to="/renta">Renta</b-button>
      <b-button variant="outline-primary" to="/formulario">Formulario</b-button>
      <b-button variant="outline-primary" to="/pelicula">Pelicula</b-button>
    </b-container>
    ESTA INICIO
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          text: "Inicio",
          active: true,
        },
      ],
    };
  },
};
</script>
