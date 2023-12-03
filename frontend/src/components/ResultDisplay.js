import React from "react";
import PropTypes from "prop-types";
import {toast} from "react-toastify";


const ResultDisplay = ({ loading, response }) => {

    const handleCopyToClipboard = () => {
        if (response) {
            navigator.clipboard
                .writeText(response.content)
                .then(() => {
                    toast("Skopiowano do schowka", {
                        type: "success",
                    });
                })
                .catch((error) => {
                    console.error("Error copying to clipboard", error);
                    toast("Wystąpił błąd podczas kopiowania do schowka", {
                        type: "error",
                    });
                });
        }
    };

    const handleSaveResult = () => {
        if (response) {
            const blob = new Blob([response.content], { type: "text/plain" });
            const url = URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.href = url;
            a.download = "wynik.txt";
            document.body.appendChild(a);
            a.click();
            URL.revokeObjectURL(url);
        }
    };

    let content = null;

    if (loading) {
        content = (
            <div className="mt-4 flex items-center">
                <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-blue-500 mx-auto"></div>
                <span className="ml-2 text-gray-700">Trwa przetwarzanie pliku...</span>
            </div>
        );
    } else if (response) {
        content = (
            <div className="mt-4">
                <h2 className="text-2xl font-semibold mb-2">Wynik:</h2>
                <p className="text-gray-700 text-xl">{response.content}</p>
                <div className="flex flex-row justify-around my-4" >
                    <button
                        onClick={handleCopyToClipboard}
                        className="bg-blue-500 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md"
                    >
                        Kopiuj do schowka
                    </button>
                    <button
                        onClick={handleSaveResult}
                        className="bg-blue-500 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-md"
                    >
                        Zapisz do pliku
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className="mt-4">{content}</div>
    );
};

ResultDisplay.propTypes = {
    loading: PropTypes.bool.isRequired,
    response: PropTypes.object,
    handleCopyToClipboard: PropTypes.func.isRequired,
};

export default ResultDisplay;