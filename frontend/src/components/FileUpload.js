import React from "react";
import PropTypes from "prop-types";
import { toast, ToastContainer } from "react-toastify";
import FileDropzone from "./FileDropzone";
import UploadForm from "./UploadForm";
import ResultDisplay from "./ResultDisplay";

const FileUpload = ({
                        onFileChange,
                        handleUpload,
                        loading,
                        response,
                        selectedLanguage,
                        onLanguageChange,
                        sliderValue,
                        onSliderChange,
                    }) => {

    return (
        <div className="w-1/2 p-6 bg-white rounded-lg shadow-md mr-5">
            <FileDropzone onFileChange={onFileChange} loading={loading} />
            <UploadForm
                handleUpload={handleUpload}
                loading={loading}
                selectedLanguage={selectedLanguage}
                onLanguageChange={onLanguageChange}
                sliderValue={sliderValue}
                onSliderChange={onSliderChange}
            />
            <ResultDisplay
                loading={loading}
                response={response}
            />
            <ToastContainer
                position="top-center"
                autoClose={2000}/>
        </div>
    );
};

FileUpload.propTypes = {
    onFileChange: PropTypes.func.isRequired,
    handleUpload: PropTypes.func.isRequired,
    loading: PropTypes.bool.isRequired,
    response: PropTypes.object,
    selectedLanguage: PropTypes.string.isRequired,
    onLanguageChange: PropTypes.func.isRequired,
};

export default FileUpload;