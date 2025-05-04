<template>
  <div class="container mx-auto px-4 py-8">
    <h1 class="text-4xl font-bold mb-8">Welcome to Property Rental</h1>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div v-for="feature in features" :key="feature.title" class="card">
        <h2 class="text-xl font-semibold mb-4">{{ feature.title }}</h2>
        <p class="text-gray-600">{{ feature.description }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const features = [
  {
    title: 'Find Your Perfect Home',
    description: 'Browse through our extensive collection of properties and find the perfect place to call home.'
  },
  {
    title: 'Easy Booking',
    description: 'Our streamlined booking process makes it simple to reserve your desired property.'
  },
  {
    title: 'Secure Payments',
    description: 'Rest easy knowing your payments are secure and your information is protected.'
  }
]
</script> 