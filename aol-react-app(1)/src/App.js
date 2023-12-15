import logo from './logo.svg';
import Home from './Home/Home';
import Login from './Login/Login';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Register from './Login/Register';
import Schedule from './Scheduling/Schedule';

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Home />}/>
          <Route path="login" element={<Login />} />
          <Route path="register" element={<Register />} />
          <Route path="schedule" element={<Schedule />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
