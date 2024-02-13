<template>
    <b-row class="mt-5">
        <b-col cols="12">
            <b-row>
                <b-col cols="8">
                    <b-form-input placeholder="Buscador..." :type="'search'" />
                    <b-form-invalid-feedback tooltip> </b-form-invalid-feedback>
                </b-col>
                <b-col cols="4">
                    <b-button>Buscar</b-button>
                </b-col>
            </b-row>
        </b-col>
    </b-row>
</template>

<script>
export default {

}
</script>

<style>
</style>