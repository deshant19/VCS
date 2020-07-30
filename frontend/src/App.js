import React, { useState } from 'react';
import './App.css';


function App() {
  const [file, setFile] = useState('')
  const [loading, setLoading] = useState(false)

  const uploadFileFirst = async e => {

    if (window.File && window.FileReader && window.FileList && window.Blob) {
      var preview1 = document.getElementById('show-text-first');
      var fileData1 = document.querySelector('input[id=first]').files[0];
      var reader1 = new FileReader()

      var textFile1 = /text.*/;

      if (fileData1.type.match(textFile1)) {
         reader1.onload = function (event) {
            preview1.innerHTML = event.target.result;
         }
      } else {
         preview1.innerHTML = "<span class='error'>It doesn't seem to be a text file!</span>";
      }
      reader1.readAsText(fileData1);

    } else {
      alert("Your browser is too old to support HTML5 File API");
    }

    const files1 = e.target.files
    const data1 = new FormData()
    data1.append('file', files1[0])
    setLoading(true)
    const res1 = await fetch(
      '	http://localhost:8080/file/upload',
      {
        method: 'POST',
        body: data1
      }
    )
    const file1 = await res1.json()

    setFile(file1.secure_url)
    setLoading(false)
  }

  const uploadFileSecond = async e => {

    if (window.File && window.FileReader && window.FileList && window.Blob) {
      var preview = document.getElementById('show-text-second');
      var fileData = document.querySelector('input[id=second]').files[0];
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

    const files = e.target.files
    const data = new FormData()
    data.append('file', files[0])
    setLoading(true)
    const res = await fetch(
      '	http://localhost:8080/file/upload',
      {
        method: 'POST',
        body: data
      }
    )
    const file = await res.json()

    setFile(file.secure_url)
    setLoading(false)
  }

  return (
    <div className="App">
      <h1>VCS File</h1>
      <div id = "show">
        <input id = "first"
          type="file"
          name="file"
          placeholder="Upload a file"
          onChange={uploadFileFirst}
        />
        <input id = "second"
          type="file"
          name="file"
          placeholder="Upload a file"
          onChange={uploadFileSecond}
        />
      </div>
      
        <div id="show-text-first"></div>
        <div id="show-text-second"></div>
      
    </div>
  )
}

export default App;
