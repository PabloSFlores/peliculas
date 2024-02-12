<template>
  <div>
    <b-container>
      <b-breadcrumb :items="items"></b-breadcrumb>
    </b-container>
    ESTAS EN DETALLES DE ESPACIO
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
          text: "Catalogo",
          to: { name: "catalogo" },
        },
        {
          text: "Renta",
          to: { name: "renta" },
        },
        {
          text: "Detalles",
          active: true,
        },
      ],
    };
  },
};
</script>

<style></style>
