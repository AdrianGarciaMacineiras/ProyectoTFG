import './App.css';
import {useNavigate, Routes, Route} from 'react-router-dom';
import FindPerson from './person/FindPerson'
import FindClient from './client/FindClient'


function App() {

  const navigate = useNavigate();

  const navigateToFindPeople = () => {
    navigate("/findPerson")
  }

  const navigateToFindClient = () => {
    navigate("/findClient")
  }

  return (
    <div>
      <Routes>
        <Route path='/findPerson' element={<FindPerson/>} />
        <Route path='/findClient' element={<FindClient/>} />
      </Routes>
    <div>
      <button onClick={() => navigateToFindPeople()}>Find Person</button>
      <button onClick={() => navigateToFindClient()}>Find Client</button>
    </div>
  </div>
  );
}

export default App;
