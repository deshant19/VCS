import React, { useState } from 'react';
import axios from'axios';
import './App.css';


function App() {
  const [file, setFile] = useState('')
  const [name, setName] = useState('')
  const [dataFiles, setDataFiles] = useState([])
  
  
  const showFile = async e => {
    setFile(e.target.files[0])
    console.log(file)
    if (window.File && window.FileReader && window.FileList && window.Blob) {
      var preview = document.getElementById('show-text-first');
      var fileData = document.querySelector('input[id=first]').files[0];
      var reader = new FileReader()

      var textFile = /text.*/;

      if (fileData.type.match(textFile)) {
         reader.onload = function (event) {
            preview.innerHTML = event.target.result;
         }
      } else {
         preview.innerHTML = "<span class='error'>It doesn't seem to be a text file!</span>";
      }
      reader.readAsText(fileData);
    } else {
      alert("Your browser is too old to support HTML5 File API");
    }
  }

  const uploadFile = async e => {
    const files = file
    const data = new FormData()
    data.append('file', files)
    const res = await fetch(
      'http://localhost:8080/file/upload',
      {
        method: 'POST',
        body: data,
        mode: 'no-cors'
      }
    )
    const result = await res.json()
    console.log(result);
  }

  const fileName = e => {
    setName(e.target.value)
  }

  const getFiles = async e => {
    
    const res = await axios(
      `http://localhost:8080/file/${name}`,
    )

    setDataFiles(res.data.entity)
    }

  return (
    <div className="App">
      <h1>VCS App</h1>

      <div id = "boxes">
        <input type="text" onChange={fileName}/>
        <button onClick={getFiles}>GET HISTORY</button>
      </div>

      <div id = "show">
        <input id = "first"
          type="file"
          name="file"
          placeholder="Upload a file"
          onChange={showFile}
        />
      </div>
      
      <div id = "show-text">
        
        <div id="data">
          {!dataFiles.length ? 
            <div><strong>No files present.</strong></div> :
            <div>
              {dataFiles.map(file => (
                <div key = {file.id}>
                  <strong>Version: {file.version}</strong>&nbsp;&nbsp;&nbsp;
                  <strong>{file.name}</strong>&nbsp;&nbsp;&nbsp;
                  <button>Revert</button>&nbsp;&nbsp;&nbsp;
                  <button>Update</button>
                </div>
              ))}
            </div>
          }
        </div>
          
        <div id="show-text-first" contentEditable="true"></div>
           
        <div id="save">
          <button onClick={uploadFile}>Save</button>
        </div>
      </div>
    </div>
  )
}

export default App;
