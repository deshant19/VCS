import React, { useState } from 'react';
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
    const res = await fetch(
      'http://localhost:8080/file/'+{name},
      {
        method: 'GET',
        mode: 'no-cors'
      }
    ).then(response => response.text())
    .then(contents => console.log(contents))
    .catch(() => console.log("Can’t access response. Blocked by browser or invalid endpoint!"))

    const data = await res.json()
    setDataFiles(data.results)
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
        <div>
          <div id="show-text-first" contentEditable="true"></div>

            <div id="data">
              {!dataFiles.length ? 
                <div>No files present.</div> :
                <div>
                  {dataFiles.map(file => (
                    <div key = {file.id}>
                      <div>{file.version}</div>
                      <div>{file.name}</div>
                      <div><button>Revert</button></div>
                      <div><button>Update</button></div>
                    </div>
                  ))}
                </div>
              }
            </div>
        </div>
        <div id="save">
          <button onClick={uploadFile}>Save</button>
        </div>
      </div>
    </div>
  )
}

export default App;
