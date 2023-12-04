import React, { useCallback, useState } from "react";
import axios from "axios";
import FileViewer from "./components/FileViewer";
import FileUpload from "./components/FileUpload";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function App() {
  const [file, setFile] = useState(null);
  const [response, setResponse] = useState("");
  const [loading, setLoading] = useState(false);
  const [sliderValue, setSliderValue] = useState(5);
  const [selectedLanguage, setSelectedLanguage] = useState("Polish");

  const onFileChange = useCallback((event) => {
    setResponse("");
    const selectedFile = event.target.files[0];
    if (selectedFile) {
      if (selectedFile.size <= 2 * 1024 * 1024) {
        setFile(selectedFile);
      } else {
        setFile(null);
        toast(
          "The file must be smaller than 2MB and have the extension .pdf, .txt, .docx, .pptx",
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
        if (error.response.data && error.response.data.exceptionMessage) {
          if (
            error.response.data.exceptionMessage.includes(
              "File content is too long"
            ) ||
            error.response.data.exceptionMessage.includes(
              "context_length_exceeded"
            )
          ) {
            toast("Text too long. Please try again with a different file.", {
              type: "error",
            });
          } else if (
            error.response.data.exceptionMessage.includes("no_text_found")
          ) {
            toast(
              "No text found in the PDF file. Please try again with a different file.",
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
              "Cannot read text from PDF file. Please try again with a different file.",
              {
                type: "error",
              }
            );
          } else if (
            error.response.data.exceptionMessage.includes(
              "File content is empty"
            )
          ) {
            toast(
              "The file contains no content. Please try again with a different file.",
              {
                type: "error",
              }
            );
          }
        } else {
          toast("Unknown error. Please try again later.", {
            type: "error",
          });
          console.error(error);
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
    <>
      <div className="bg-white p-4 text-black shadow-xl flex items-center">
        <img className="mr-2 max-h-14 w-70" src="/logo2.jpg" />
        <p className="text-purple-500 text-2xl">File summarizer</p>
      </div>
      <div className="mx-8  my-8 flex flex-row">
        <FileUpload
          onFileChange={onFileChange}
          handleUpload={handleUpload}
          loading={loading}
          response={response}
          selectedLanguage={selectedLanguage}
          onLanguageChange={handleLanguageChange}
          sliderValue={sliderValue}
          onSliderChange={handleSliderChange}
        />
        <FileViewer viewPdf={file} />
        <ToastContainer position="top-center" autoClose={2000} />
      </div>
    </>
  );
}

export default App;
