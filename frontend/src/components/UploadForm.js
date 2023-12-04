import React from "react";
import PropTypes from "prop-types";

const UploadForm = ({
  handleUpload,
  loading,
  selectedLanguage,
  onLanguageChange,
  sliderValue,
  onSliderChange,
}) => {
  return (
    <div className="flex flex-row justify-around mt-3">
      <div className="flex flex-col">
        <h6>Select the language of the summary:</h6>
        <select
          className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          value={selectedLanguage}
          onChange={onLanguageChange}
          disabled={loading}
        >
          <option value="Polish">Polish</option>
          <option value="English">English</option>
          <option value="Korean">Korean</option>
          <option value="German">German</option>
        </select>
      </div>
      <div>
        <label htmlFor="slider" className="text-gray-700 block">
          Select detail level:
        </label>
        <input
          type="range"
          id="slider"
          name="slider"
          min="1"
          max="10"
          value={sliderValue}
          onChange={onSliderChange}
          disabled={loading}
        />
        <p className="text-gray-700">Value: {sliderValue}</p>
      </div>
      <button
        onClick={handleUpload}
        className={`bg-purple-500 hover-bg-purple-700 text-white font-semibold py-2 px-4 rounded-md mt-2 ${
          loading ? "opacity-50 cursor-not-allowed" : ""
        }`}
        disabled={loading}
      >
        Send file
      </button>
    </div>
  );
};

UploadForm.propTypes = {
  handleUpload: PropTypes.func.isRequired,
  loading: PropTypes.bool.isRequired,
  selectedLanguage: PropTypes.string.isRequired,
  onLanguageChange: PropTypes.func.isRequired,
  sliderValue: PropTypes.number.isRequired,
  onSliderChange: PropTypes.func.isRequired,
};

export default UploadForm;
