<template>
  <div>
    <b-container>
      <b-breadcrumb :items="items"></b-breadcrumb>
      <b-button variant="outline-primary" to="/oferta">Oferta</b-button>
    </b-container>
    ESTAS EN INFORMACION DE ESPACIO
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
          text: "Informacion",
          active: true,
        },
      ],
    };
  },
};
</script>

<style></style>
