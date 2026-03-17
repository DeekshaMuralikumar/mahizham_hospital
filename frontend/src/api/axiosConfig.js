import axios from "axios";

const API_BASE = process.env.REACT_APP_API_BASE || "https://mahizham-hospital.onrender.com";

const axiosConfig = axios.create({
  baseURL: API_BASE,
  withCredentials: true,
});

// Add a request interceptor to include the token in headers
axiosConfig.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axiosConfig;
