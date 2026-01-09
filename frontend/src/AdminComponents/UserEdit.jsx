import React, { useEffect, useState } from 'react';
import { Button, Select, MenuItem, Alert } from "@mui/material";
import axios from 'axios';

export default function UserEdit({ setExportHandler }) {
    const [renderUsers, setRenderUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [editingId, setEditingId] = useState(null);
    const [saving, setSaving] = useState(false);
    
    useEffect(() => {
        axios
            .get(`${import.meta.env.VITE_API_URL}/users`, { withCredentials: true })
            .then(response => {
                setRenderUsers(response.data);
                setError(null);
            })
            .catch(error => {
                console.error('Error fetching users:', error);
                setError('Neuspješno učitavanje korisnika.');
            })
            .finally(() => setLoading(false));
    }, []);
    
    useEffect(() => {
        setExportHandler(() => exportUsers);
        return () => setExportHandler(null);
    }, [setExportHandler]);
    
    const exportUsers = async (format) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_API_URL}/users/export?format=${format}`,
                {
                    withCredentials: true,
                    responseType: "blob",
                }
            );
            downloadFile(response.data, `users.${format}`);
        } catch (error) {
            console.error("Error exporting users: ", error);
            alert("Greška prilikom izvoza korisnika.");
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm("Jeste li sigurni da želite obrisati ovog korisnika?")) return;

        try {
            await axios.delete(`${import.meta.env.VITE_API_URL}` + `/users/${id}`, { withCredentials: true });
            setRenderUsers((prev) => prev.filter((user) => user.id !== id));
        } catch (error) {
            console.error("Error deleting user:", error);
            alert("Greška prilikom brisanja korisnika.");
        }
    };

    const handleRoleChange = async (userId, newRole) => {
        setSaving(true);
        try {
            const response = await axios.put(
                `${import.meta.env.VITE_API_URL}/users/${userId}/role`,
                { uloga: newRole },
                { withCredentials: true }
            );
            setRenderUsers(prev => 
                prev.map(user => 
                    user.id === userId ? { ...user, uloga: newRole } : user
                )
            );
            setEditingId(null);
        } catch (error) {
            console.error("Error updating user role:", error);
            alert("Greška prilikom ažuriranja uloge korisnika.");
        } finally {
            setSaving(false);
        }
    };

    const handleEditClick = (userId) => {
        setEditingId(userId);
    };

    const handleCancelEdit = () => {
        setEditingId(null);
    };

    if (loading) return <div>Učitavanje korisnika...</div>;
    if (error) return <Alert severity="error">{error}</Alert>;
    
    return (
        <div>
            {renderUsers.length === 0 ? (
                <div>Nema korisnika za prikaz.</div>
            ) : (
                renderUsers.map((user) => (
                    <div
                        key={user.id}
                        style={{
                            border: "1px solid grey",
                            padding: "8px",
                            marginBottom: "5px",
                            display: "flex",
                            justifyContent: "space-between",
                            alignItems: "center",
                        }}
                    >
                    <div style={{ flexGrow: 1, paddingRight: "15px" }}>
                        <h4 style = {{ margin: "0 0 8px 0" }}>
                            {user.ime} {user.prezime}
                            {user.uloga === "ADMIN" && (<span style={{
                                color: "white", 
                                marginLeft: "10px",
                                backgroundColor: "#ff6b6b",
                                padding: "2px 6px",
                                borderRadius: "4px"
                                }}>ADMIN</span>
                            )}
                        </h4>
                        <div>Email korisnika: {user.email}</div>
                        <div>Telefonski broj: {user.telefon}</div>
                        <div>Država: {user.drzava}</div>
                        <div>Mjesto: {user.mjesto}</div>
                        <div>Poštanski broj: {user.postanskiBroj}</div>
                        <div>Uloga korisnika: {editingId === user.id ? (
                            <div>
                                <Select
                                    value={user.uloga}
                                    onChange={(e) => handleRoleChange(user.id, e.target.value)}
                                    size="small"
                                    disabled={saving}
                                >
                                    <MenuItem value="KORISNIK">Korisnik</MenuItem>
                                    <MenuItem value="ADMIN">Administrator</MenuItem>
                                    <MenuItem value="RECEPCIONIST">Recepcionist</MenuItem>
                                </Select>
                                <Button size="small" onClick={handleCancelEdit} disabled={saving}> Odustani </Button>
                            </div>
                        ) : (
                            <div>
                                <span> {user.uloga} </span>
                                <Button size="small" variant="outlined" onClick={() => handleEditClick(user.id)}>Edit</Button>
                            </div>
                        )}
                        </div>
                        <Button
                            variant="contained"
                            color="error"
                            onClick={() => handleDelete(user.id)}
                            disabled={user.uloga === "ADMIN"}
                            size="small"
                        >
                        Izbriši
                        </Button>
                    </div>
                    </div>
                ))
            )}
        </div>
    );
}

function downloadFile(blob, filename) {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = filename;
    a.click();
    window.URL.revokeObjectURL(url);
}