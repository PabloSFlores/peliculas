<template>
  <div>
    <b-container>
      <b-breadcrumb :items="items"></b-breadcrumb>
      <b-button variant="outline-primary" to="/detalles">Detalles</b-button>
    </b-container>
    ESTAS EN CATALOGO DE ESPACIOS
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
          to: { name: "renta" },
        },
        {
          text: "Catalogo",
          active: true,
        },
      ],
    };
  },
};
</script>

<style></style>
