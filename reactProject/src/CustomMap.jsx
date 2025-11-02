import React from "react";
import { Map, AdvancedMarker } from "@vis.gl/react-google-maps";

const CustomMap = () => {
  const position = { lat: 45.815399, lng: 15.966568}; 

  return (
    <div className="map-container" style={{ height: "400px", width: "100%" }}>
      <Map
        style={{ borderRadius: "20px" }}
        defaultZoom={13}
        defaultCenter={position}
        gestureHandling="greedy"
        disableDefaultUI
        mapId={import.meta.env.VITE_GOOGLE_MAP_ID}
      >
        <AdvancedMarker position={position}>
          <div style={{ color: "red", fontWeight: "bold" }}>📍</div>
        </AdvancedMarker>
      </Map>
    </div>
  );
};

export default CustomMap;
