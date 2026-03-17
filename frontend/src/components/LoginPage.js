import axiosConfig from "../api/axiosConfig";

function LoginPage() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const API_BASE = process.env.REACT_APP_API_BASE || "https://mahizham-hospital.onrender.com";

  const handleLogin = async () => {
    const enteredEmail = email.trim();
    const enteredPassword = password.trim();

    // Dummy Admin 
    if (enteredEmail === "admin@hospital.com" && enteredPassword === "admin") {
      const dummyAdmin = {
        id: 0,
        name: "Super Admin",
        email: "admin@hospital.com",
        role: "ADMIN"
      };
      localStorage.setItem("role", dummyAdmin.role);
      localStorage.setItem("email", dummyAdmin.email);
      localStorage.setItem("userId", dummyAdmin.id);
      localStorage.setItem("name", dummyAdmin.name);
      navigate("/admin");
      return;
    }

    try {
      const response = await axiosConfig.post("/api/auth/login", {
        email: enteredEmail,
        password: enteredPassword
      });

      const user = response.data;
      localStorage.setItem("token", user.token);
      localStorage.setItem("role", user.role);
      localStorage.setItem("email", user.email);
      localStorage.setItem("userId", user.id);
      localStorage.setItem("name", user.name);

      if (user.role === "ADMIN") navigate("/admin");
      else if (user.role === "DOCTOR") navigate("/doctor");
      else if (user.role === "PATIENT") navigate("/patient");
    } catch (error) {
      console.error("Login Error:", error);
      const errorMsg = error.response?.data?.message || error.response?.data || "Invalid credentials or network error";
      alert(typeof errorMsg === 'string' ? errorMsg : JSON.stringify(errorMsg));
    }
  };

  return (
    <div className="auth-wrapper">
      <h1 className="brand-header">Mahizham <span>Hospital</span></h1>
      <div className="login-container">
        <h2 style={{ marginBottom: "25px" }}>Sign In</h2>

        <input
          type="email"
          placeholder="Enter Email"
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Enter Password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button onClick={handleLogin}>Login</button>

        <div className="signup-footer">
          New User?
          <button
            className="signup-link"
            onClick={() => navigate("/signup")}
          >
            Sign Up
          </button>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;