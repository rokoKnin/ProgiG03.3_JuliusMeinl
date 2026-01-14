import React, { useEffect, useState } from 'react';
import { Button, Select, MenuItem, Alert, CircularProgress } from "@mui/material";
import { Unstable_NumberInput as BaseNumberInput, numberInputClasses } from "@mui/base/Unstable_NumberInput";
import { styled } from "@mui/system";
import axios from 'axios';

const IMAGE_MAPPING = {
    'Bazen': '/pool2.jpg',
    'Restoran': '/restaurant.jpg',
    'Teretana': '/gym.jpg'
}


export default function ExtraContentEdit( { setExportHandler}) {
    const [extraContent, setExtraContent] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [saving, setSaving] = useState(false);

    // Function to get image path based on content name
    const getImagePath = (contentName) => {
        if (IMAGE_MAPPING[contentName]) {
            return IMAGE_MAPPING[contentName];
        }
        
        const matchedKey = Object.keys(IMAGE_MAPPING).find(key => 
            contentName.toLowerCase().includes(key.toLowerCase()) || 
            key.toLowerCase().includes(contentName.toLowerCase())
        );
        
        if (matchedKey) {
            return IMAGE_MAPPING[matchedKey];
        }

        return '../public/sea.png';
    };

    useEffect(() => {
        setLoading(true);
        axios
            .get(`${import.meta.env.VITE_API_URL}/api/extraContentEdit`, { withCredentials: true })
            .then((response) => {
                const ImgExtraContent = response.data.map(content => ({
                    ...content, 
                    image: getImagePath(content.vrsta)
                }));
                setExtraContent(ImgExtraContent);
                setError(null);
            })
            .catch((error) => {
                console.error("Error fetching extra content:", error)
                setError("Greška prilikom učitavanja dodatnog sadržaja.");
            })
            .finally(() => {setLoading(false)});
    }, []);

    useEffect(() => {
        setExportHandler(() => exportExtraContent);
        return () => setExportHandler(null);
    }, [setExportHandler]);

    const exportExtraContent = async (format) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_API_URL}/api/extraContentEdit/export?format=${format}`,
                { withCredentials: true, responseType: "blob", }
            );
            downloadFile(response.data, `extraContent.${format}`);
        } catch (error) {
            console.error("Error exporting extra content: ", error);
            alert("Greška prilikom izvoza dodatnog sadržaja.");
        }
    };

    const handleSave = async (id) => {
        const contentToSave = extraContent.find(content => content.id === id);
        if (!contentToSave) return;
        setSaving(true);
        try {
            await axios.put(
                `${import.meta.env.VITE_API_URL}/api/extraContentEdit/${id}`,
                contentToSave,
                { withCredentials: true }
            );
        } catch (error) {
            console.error("Error saving extra content:", error);
            alert("Greška prilikom ažuriranja dodatnog sadržaja.");
        } finally {
            setSaving(false);
        }
    };

    const handlePriceChange = (id, newPrice) => {
        setExtraContent(prev =>
            prev.map(content => 
                content.id === id ? { ...content, cijena: newPrice } : content
            )
        );
    };

    const handleStatusChange = (id, selectedStatus) => {
        setExtraContent(prev =>
            prev.map(content => 
                content.id === id ? { ...content, status: selectedStatus } : content
            )
        );
    };

    if (loading) return <div style={{ textAlign: 'center', marginTop: '2rem' }}><CircularProgress /></div>;
    if (error) return <Alert severity="error">{error}</Alert>;

    return (
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '16px', justifyContent: 'flex-start' }}>
            {extraContent.map((content) => (
                <div key={content.id} className="dataCard" style={{ border: '1px solid #ddd', borderRadius: '8px', padding: '16px',flex: '0 0 auto', marginBottom: '16px', width: '280px' }}>
                    <h3>{content.vrsta}</h3>
                    <img src={content.image} alt={content.vrsta} style={{ width: '100%', height: '160px', objectFit: 'cover', borderRadius: '5px'}}/>
                    <div><strong>Cijena:</strong> <NumberInput
                                    value={content.cijena || 1}
                                    onChange={(e, val) => handlePriceChange(content.id, val)}
                                    min={1}
                                    max={999}
                                />
                    </div>
                    <div><strong>Status:</strong><Select
                            value={content.status}
                            onChange={(e) => handleStatusChange(content.id, e.target.value)}
                            size="small"
                            disabled={saving}
                        >
                            <MenuItem value="ENABLED">Omogući</MenuItem>
                            <MenuItem value="DISABLED">Onemogući</MenuItem>
                        </Select></div>
                    <Button variant="contained" onClick={() => handleSave(content.id)}>Spremi</Button>
                </div>
            ))}
        </div>
    )
}

function downloadFile(blob, filename) {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = filename;
    a.click();
    window.URL.revokeObjectURL(url);
}

const NumberInput = React.forwardRef(function CustomNumberInput(props, ref) {
  return (
    <BaseNumberInput
      slots={{
        root: StyledInputRoot,
        input: StyledInputElement,
        incrementButton: StyledButton,
        decrementButton: StyledButton,
      }}
      slotProps={{
        incrementButton: { children: "▴" },
        decrementButton: { children: "▾" },
      }}
      {...props}
      ref={ref}
    />
  );
});

const blue = {
  100: "#DAECFF",
  200: "#80BFFF",
  400: "#3399FF",
  500: "#007FFF",
  600: "#0072E5",
};

const grey = {
  50: "#F3F6F9",
  100: "#E5EAF2",
  200: "#DAE2ED",
  300: "#C7D0DD",
  400: "#B0B8C4",
  500: "#9DA8B7",
  600: "#6B7A90",
  700: "#434D5B",
  800: "#303740",
  900: "#1C2025",
};

const StyledInputRoot = styled("div")(
  ({ theme }) => `
  font-family: 'IBM Plex Sans', sans-serif;
  font-weight: 400;
  border-radius: 8px;
  color: ${theme.palette.mode === "dark" ? grey[300] : grey[900]};
  background: ${theme.palette.mode === "dark" ? grey[900] : "#fff"};
  border: 1px solid ${theme.palette.mode === "dark" ? grey[700] : grey[200]};
  box-shadow: 0 2px 2px ${theme.palette.mode === "dark" ? grey[900] : grey[50]};
  display: grid;
  grid-template-columns: 1fr 19px;
  grid-template-rows: 1fr 1fr;
  overflow: hidden;
  column-gap: 8px;
  padding: 4px;
  &.${numberInputClasses.focused} {
    border-color: ${blue[400]};
    box-shadow: 0 0 0 3px ${
      theme.palette.mode === "dark" ? blue[600] : blue[200]
    };
  }
  &:hover {
    border-color: ${blue[400]};
  }
`
);

const StyledInputElement = styled("input")`
  font-size: 0.875rem;
  font-family: inherit;
  font-weight: 400;
  line-height: 1.5;
  grid-column: 1/2;
  grid-row: 1/3;
  color: inherit;
  background: inherit;
  border: none;
  border-radius: inherit;
  padding: 8px 12px;
  outline: 0;
`;

const StyledButton = styled("button")(
  ({ theme }) => `
  display: flex;
  justify-content: center;
  align-items: center;
  width: 19px;
  height: 19px;
  border: 1px solid ${
    theme.palette.mode === "dark" ? grey[800] : grey[200]
  };
  background: ${theme.palette.mode === "dark" ? grey[900] : grey[50]};
  color: ${theme.palette.mode === "dark" ? grey[200] : grey[900]};
  cursor: pointer;
  &:hover {
    background: ${blue[400]};
    color: white;
  }
  &.${numberInputClasses.incrementButton} {
    grid-column: 2/3;
    grid-row: 1/2;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
  }
  &.${numberInputClasses.decrementButton} {
    grid-column: 2/3;
    grid-row: 2/3;
    border-bottom-left-radius: 4px;
    border-bottom-right-radius: 4px;
  }
`
);
