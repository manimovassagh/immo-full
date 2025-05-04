<template>
  <div class="container mx-auto px-4 py-8">
    <div class="flex justify-between items-center mb-8">
      <h1 class="text-3xl font-bold">Properties</h1>
      <button class="btn btn-primary" @click="showAddPropertyModal = true">Add Property</button>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div v-for="property in properties" :key="property.id" class="card">
        <img :src="property.imageUrl" :alt="property.title" class="w-full h-48 object-cover rounded-t-lg" />
        <div class="p-4">
          <h2 class="text-xl font-semibold mb-2">{{ property.title }}</h2>
          <p class="text-gray-600 mb-4">{{ property.description }}</p>
          <div class="flex justify-between items-center">
            <span class="text-lg font-bold text-primary-600">${{ property.price }}/month</span>
            <button class="btn btn-secondary" @click="viewProperty(property)">View Details</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Add Property Modal -->
    <div v-if="showAddPropertyModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
      <div class="bg-white rounded-lg p-8 max-w-md w-full">
        <h2 class="text-2xl font-bold mb-6">Add New Property</h2>
        <form @submit.prevent="handleAddProperty">
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700">Title</label>
              <input v-model="newProperty.title" type="text" required class="input" />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700">Description</label>
              <textarea v-model="newProperty.description" required class="input" rows="3"></textarea>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700">Price per month</label>
              <input v-model="newProperty.price" type="number" required class="input" />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700">Image</label>
              <input type="file" @change="handleImageChange" accept="image/*" required class="input" />
            </div>
          </div>
          <div class="mt-6 flex justify-end space-x-4">
            <button type="button" class="btn btn-secondary" @click="showAddPropertyModal = false">Cancel</button>
            <button type="submit" class="btn btn-primary">Add Property</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

interface Property {
  id: number
  title: string
  description: string
  price: number
  imageUrl: string
}

const properties = ref<Property[]>([])
const showAddPropertyModal = ref(false)
const newProperty = ref({
  title: '',
  description: '',
  price: 0,
  image: null as File | null
})

const fetchProperties = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/properties')
    properties.value = response.data
  } catch (error) {
    console.error('Error fetching properties:', error)
  }
}

const handleImageChange = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files && target.files.length > 0) {
    newProperty.value.image = target.files[0]
  }
}

const handleAddProperty = async () => {
  try {
    const formData = new FormData()
    formData.append('title', newProperty.value.title)
    formData.append('description', newProperty.value.description)
    formData.append('price', newProperty.value.price.toString())
    if (newProperty.value.image) {
      formData.append('image', newProperty.value.image)
    }

    await axios.post('http://localhost:8080/api/properties', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })

    showAddPropertyModal.value = false
    await fetchProperties()
  } catch (error) {
    console.error('Error adding property:', error)
    alert('Failed to add property. Please try again.')
  }
}

const viewProperty = (property: Property) => {
  // Implement property details view
  console.log('View property:', property)
}

onMounted(() => {
  fetchProperties()
})
</script> 