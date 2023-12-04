import React from "react";
import DocViewer, {DocViewerRenderers} from "@cyntler/react-doc-viewer";
import 'react-pdf/dist/Page/TextLayer.css';
import 'react-pdf/dist/Page/AnnotationLayer.css';

const FileViewer = ({ viewPdf }) => {
    console.log(viewPdf)
    return (
        <div className="p-6 bg-white rounded-lg shadow-md w-1/2">
            <div className="mt-4">
                <h2 className="text-xl font-semibold mb-2">Preview of the selected file:</h2>
                {viewPdf ? (
                    <DocViewer pluginRenderers={DocViewerRenderers} documents={[{ uri: window.URL.createObjectURL(viewPdf), fileName: viewPdf.name }]} />
                ) : (
                    <p>A preview of the file you selected will be available here.</p>
                )}
            </div>
        </div>
    );
};

export default FileViewer;
