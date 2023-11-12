import axios from "axios";
import "./App.css";
import React, { useCallback, useState } from "react";
import { toast, ToastContainer } from "react-toastify";
import icon from "./logo.svg";

function App() {
  const [file, setFile] = useState(null);
  const [response, setResponse] = useState("");
  const [loading, setLoading] = useState(false);
  const [sliderValue, setSliderValue] = useState(5);
  const [selectedLanguage, setSelectedLanguage] = useState("polish");

  const onFileChange = useCallback((event) => {
    setResponse("");
    const selectedFile = event.target.files[0];
    if (selectedFile) {
      if (selectedFile.size <= 2 * 1024 * 1024) {
        setFile(selectedFile);
      } else {
        setFile(null);
        toast(
          "Plik musi być mniejszy niż 2MB i mieć rozszerzenie .pdf, .txt, .docx",
          {
            type: "error",
          }
        );
      }
    }
  }, []);

  const handleUpload = async () => {
    if (file) {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("language", selectedLanguage);
      formData.append("contextLength", sliderValue);
      formData.append(
        "fileExtension",
        file.name.split(".").pop().toLowerCase()
      );

      try {
        setLoading(true);
        const response = await axios.post(
          "http://localhost:8080/api/v1/extractor",
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          }
        );
        setResponse(response.data);
        setLoading(false);
      } catch (error) {
        if (
          error.response.data.exceptionMessage.includes(
            "context_length_exceeded"
          )
        ) {
          toast("Zbyt długi tekst. Spróbuj ponownie z innym plikiem.", {
            type: "error",
          });
        } else if (
          error.response.data.exceptionMessage.includes("no_text_found")
        ) {
          toast(
            "Nie znaleziono tekstu w pliku PDF. Spróbuj ponownie z innym plikiem.",
            {
              type: "error",
            }
          );
        } else if (
          error.response.data.exceptionMessage.includes(
            "Cannot read text from PDF file"
          )
        ) {
          toast(
            "Nie można odczytać tekstu z pliku PDF. Spróbuj ponownie z innym plikiem.",
            {
              type: "error",
            }
          );
        }
        setResponse("");
        setLoading(false);
      }
    }
  };

  const handleLanguageChange = (event) => {
    setSelectedLanguage(event.target.value);
  };

  const handleSliderChange = (event) => {
    setSliderValue(event.target.value);
  };

  return (
    <div className="App">
      <header className="App-header"></header>
      <ToastContainer position="top-center" autoClose={2000} />
    </div>
  );
}

export default App;
