<template>
  <div>
    <b-container>
      <b-breadcrumb :items="items"></b-breadcrumb>
      <b-button variant="outline-primary" to="/informacion">Informacion</b-button>
      <b-button variant="outline-primary" to="/catalogo">Catalogo</b-button>
    </b-container>
    ESTAS EN RENTA TU ESPACIO
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          text: "Inicio",
          to: { name: "inicio" },
        },
        {
          text: "Renta",
          active: true,
        },
      ],
    };
  },
};
</script>

<style></style>
