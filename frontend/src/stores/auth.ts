import { defineStore } from 'pinia'
import axios from 'axios'

interface User {
  id: number
  email: string
  name: string
}

interface AuthState {
  token: string | null
  user: User | null
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    token: localStorage.getItem('token'),
    user: null
  }),
  
  getters: {
    isAuthenticated: (state) => !!state.token,
    currentUser: (state) => state.user
  },
  
  actions: {
    async login(email: string, password: string) {
      try {
        const response = await axios.post('http://localhost:8080/api/auth/login', {
          email,
          password
        })
        
        const { token, user } = response.data
        this.token = token
        this.user = user
        localStorage.setItem('token', token)
        
        return true
      } catch (error) {
        console.error('Login error:', error)
        return false
      }
    },
    
    async register(name: string, email: string, password: string) {
      try {
        const response = await axios.post('http://localhost:8080/api/auth/register', {
          name,
          email,
          password
        })
        
        const { token, user } = response.data
        this.token = token
        this.user = user
        localStorage.setItem('token', token)
        
        return true
      } catch (error) {
        console.error('Registration error:', error)
        return false
      }
    },
    
    logout() {
      this.token = null
      this.user = null
      localStorage.removeItem('token')
    }
  }
}) 