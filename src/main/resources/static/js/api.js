// API Client for CodeClash LAN Platform
const API = {
  async request(endpoint, options = {}) {
    const defaultHeaders = {
      'Content-Type': 'application/json',
    };

    const config = {
      ...options,
      headers: {
        ...defaultHeaders,
        ...options.headers,
      },
    };

    try {
      const response = await fetch(endpoint, config);
      const data = await response.json().catch(() => ({}));
      
      if (!response.ok) {
        throw new Error(data.message || data.error || `HTTP error ${response.status}`);
      }
      return data;
    } catch (err) {
      console.error(`API Error [${endpoint}]:`, err);
      throw err;
    }
  },

  // Auth Endpoints
  async register(username, email, password) {
    return this.request('/api/auth/register', {
      method: 'POST',
      body: JSON.stringify({ username, email, password }),
    });
  },

  async login(usernameOrEmail, password) {
    return this.request('/api/auth/login', {
      method: 'POST',
      body: JSON.stringify({ usernameOrEmail, password }),
    });
  },

  async logout() {
    return this.request('/api/auth/logout', { method: 'POST' });
  },

  async getCurrentUser() {
    return this.request('/api/auth/me');
  },

  // Problems
  async getProblems() {
    return this.request('/api/problems');
  },

  async getProblem(id) {
    return this.request(`/api/problems/${id}`);
  }
};
