import React, { useState } from 'react';
import './App.css';


function App() {
  const [file, setFile] = useState('')
  const [loading, setLoading] = useState(false)

  const uploadFile = async e => {
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
      <h1>Upload File</h1>
      <input
        type="file"
        name="file"
        placeholder="Upload a file"
        onChange={uploadFile}
      />
      {loading ? (
        <h3>Loading...</h3>
      ) : (
        <textarea value={file} style={{ width: '300px' }} />
      )}
    </div>
  )
}

export default App;
