import './App.css';
import {useNavigate, Routes, Route} from 'react-router-dom';
import FindPerson from './person/FindPerson'
import FindClient from './client/FindClient'
import FindSkill from './skill/FindSkill'
import FindProject from './project/FindProject'
import FindPosition from './position/FindPosition'
import FindTeam from './team/FindTeam'


function App() {

  const navigate = useNavigate();

  const navigateToFindPeople = () => {
    navigate("/findPerson")
  }

  const navigateToFindClient = () => {
    navigate("/findClient")
  }

  const navigateToFindSkill = () => {
    navigate("/findSkill")
  }

  const navigateToFindProject = () => {
    navigate("/findProject")
  }

  const navigateToFindPosition = () => {
    navigate("/findPosition")
  }

  const navigateToFindTeam = () => {
    navigate("/findTeam")
  }

  return (
    <div>
      <Routes>
        <Route path='/findPerson' element={<FindPerson/>} />
        <Route path='/findClient' element={<FindClient/>} />
        <Route path='/findSkill' element={<FindSkill/>} />
        <Route path='/findProject' element={<FindProject/>} />
        <Route path='/findPosition' element={<FindPosition/>} />
        <Route path='/findTeam' element={<FindTeam/>} />
      </Routes>
    <div>
      <button onClick={() => navigateToFindPeople()}>Find Person</button>
      <button onClick={() => navigateToFindClient()}>Find Client</button>
      <button onClick={() => navigateToFindSkill()}>Find Skill</button>
      <button onClick={() => navigateToFindProject()}>Find Project</button>
      <button onClick={() => navigateToFindPosition()}>Find Position</button>
      <button onClick={() => navigateToFindTeam()}>Find Team</button>
    </div>
  </div>
  );
}

export default App;
