import './App.css';
import React from 'react';
import { useState,useEffect } from 'react';




export default function App() {
  const [showDialog,setShowDialog] = useState(false)

  const changeShowDialog = ()=>{
    setShowDialog(!showDialog)
  }
  return (
      <div>
         <header className="App-header">
        <h1>
          Your job applications
        </h1>
      </header>
      <MainComponent></MainComponent>
      <AddApplicationButton onClick = {changeShowDialog}></AddApplicationButton>
      <FormDialog visible={showDialog}></FormDialog>
      </div>
     




  );
}


function MainComponent(){
  
 
  const [applications,setApplications] = useState([])
  useEffect(()=>{
    fetch("http://localhost:8080/applications").then((res)=>{
    res.json().then((data)=>
    setApplications(data))
  })
  },[])
  
  
  if(applications.length===0){
    return(
      <div  className="noApplication">
      <h3>
        You didn't apply to any jobs yet
      </h3>
      </div>

    )
  }
  else{
    return(

        applications.map((application)=>{
            return <JobApplication companyName={application["companyName"]} companyEmail={application["companyEmail"]} id={application["id"]} status={application["applicationStatus"]}></JobApplication>
            })
      
      
    )
  }


}

function JobApplication({companyName,companyEmail,id,status}){
  return (
    <div class="jobApplicationRow">
      <span>
        {companyName}
      </span>
      <span>
        {companyEmail}
      </span>
      <StatusSelect id={id} status={status} companyName={companyName} companyEmail={companyEmail}></StatusSelect>
    </div>
  )
}

function StatusSelect({id,status,companyName,companyEmail}){
  const [options,setOptions] = useState([])

  useEffect(()=>{
    fetch("http://localhost:8080/status").then((res)=>{
    res.json().then((data)=>
    setOptions(data))
  })
  })
 

  const updateStatus = async (newStatus)=>{
      console.log(newStatus)
      await fetch("http://localhost:8080/applications/"+id,{
        method:"PUT",
        body:JSON.stringify({
          status:newStatus,
          companyEmail:companyEmail,
          companyName:companyName
        }),
        headers: {
          'Content-Type': 'application/json'
        }
      })
    
  }
  return (
    <div>
      <select className="statusSelect" onChange={(e)=>updateStatus(e.target.value)}>
        {options.map((val)=>{
          if(val==status)
            return <option selected="selected" value={val}>{val}</option>
          return <option value={val}>{val}</option>
        }

        )}
      </select>
    </div>
  )
}

function AddApplicationButton({onClick}){
  return(
    <button className="addButton" onClick={onClick}>
      +
    </button>
  )
}
function FormDialog({visible}){

  const [companyName,setCompanyName] = useState("")
  const [companyEmail,setCompanyEmail] = useState("")

  let handleSubmit = async () => {

    await fetch("http://localhost:8080/applications",{
    method:"POST",
    body:JSON.stringify({
      companyName:companyName,
      companyEmail:companyEmail
    }),
    headers: {
      'Content-Type': 'application/json'
    }
  })
  }

  if (visible){
    return(
      <dialog open>
        <form onSubmit={handleSubmit} className = "form">
          <input className="textField" type="text" value={companyName} placeholder='Company Name' onChange={(e)=>setCompanyName(e.target.value)}></input>
          <input className="textField" type="text" value={companyEmail} placeholder='Company Email' onChange={(e)=>setCompanyEmail(e.target.value)}></input>
          <button type="submit" className="submitButton">
              Create job application
          </button>
        </form>
      </dialog>
    )
  }
  else{
    return(
      <div></div>
    )
  }
 
}