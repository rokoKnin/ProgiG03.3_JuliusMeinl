import React, { useEffect, useState } from 'react';
import { Button, Select, MenuItem, Alert, CircularProgress } from "@mui/material";
import axios from 'axios';

export default function UserEdit({ setExportHandler }) {
    const [renderUsers, setRenderUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [editingId, setEditingId] = useState(null);
    const [editedRoles, setEditedRoles] = useState({});
    const [saving, setSaving] = useState(false);

    // Mapa za prikaz uloga (frontend-friendly)
    const ulogaDisplayMap = {
        REGISTRIRAN: "Korisnik",
        ZAPOSLENIK: "Recepcionist",
        VLASNIK: "Administrator",
        NEREGISTRIRAN: "Gost"
    };

    // Dohvat korisnika
    useEffect(() => {
        axios
            .get(`${import.meta.env.VITE_API_URL}/api/users`, { withCredentials: true })
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

    // Export handler
    useEffect(() => {
        setExportHandler(() => exportUsers);
        return () => setExportHandler(null);
    }, [setExportHandler]);

    const exportUsers = async (format) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_API_URL}/api/users/export?format=${format}`,
                { withCredentials: true, responseType: "blob" }
            );
            downloadFile(response.data, `users.${format}`);
        } catch (error) {
            console.error("Error exporting users: ", error);
            alert("Greška prilikom izvoza korisnika.");
        }
    };

    // Brisanje korisnika
    const handleDelete = async (id) => {
        if (!window.confirm("Jeste li sigurni da želite obrisati ovog korisnika?")) return;

        try {
            await axios.delete(`${import.meta.env.VITE_API_URL}/api/users/${id}`, { withCredentials: true });
            setRenderUsers(prev => prev.filter(user => user.id !== id));
        } catch (error) {
            console.error("Error deleting user:", error);
            alert("Greška prilikom brisanja korisnika.");
        }
    };

    // Lokalne promjene uloga
    const handleRoleSelect = (userId, newRole) => {
        setEditedRoles(prev => ({ ...prev, [userId]: newRole }));
    };

    const handleSaveRole = async (userId) => {
        const newRole = editedRoles[userId];
        if (!newRole) return;
        setSaving(true);
        try {
            await axios.put(
                `${import.meta.env.VITE_API_URL}/api/users/${userId}/role`,
                { uloga: newRole },
                { withCredentials: true }
            );
            setRenderUsers(prev =>
                prev.map(user =>
                    user.id === userId ? { ...user, uloga: newRole } : user
                )
            );
            setEditingId(null);
            setEditedRoles(prev => {
                const copy = { ...prev };
                delete copy[userId];
                return copy;
            });
        } catch (error) {
            console.error("Error updating user role:", error);
            alert("Greška prilikom ažuriranja uloge korisnika.");
        } finally {
            setSaving(false);
        }
    };

    const handleEditClick = (userId, currentRole) => {
        setEditingId(userId);
        setEditedRoles(prev => ({ ...prev, [userId]: currentRole }));
    };

    const handleCancelEdit = () => {
        setEditingId(null);
        setEditedRoles({});
    };

    if (loading) return <div style={{ textAlign: 'center', marginTop: '2rem' }}><CircularProgress /></div>;
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
                            <h4 style={{ margin: "0 0 8px 0" }}>
                                <div>{user.ime} {user.prezime}</div>
                                {user.uloga === "VLASNIK" && (
                                    <span style={{
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
                            <div>
                                Uloga korisnika: {editingId === user.id ? (
                                <div style={{ display: 'flex', alignItems: 'center', gap: '8px', marginTop: '5px' }}>
                                    <Select
                                        value={editedRoles[user.id] ?? user.uloga}
                                        onChange={(e) => handleRoleSelect(user.id, e.target.value)}
                                        size="small"
                                        disabled={saving}
                                    >
                                        <MenuItem value="REGISTRIRAN">Korisnik</MenuItem>
                                        <MenuItem value="VLASNIK">Administrator</MenuItem>
                                        <MenuItem value="ZAPOSLENIK">Recepcionist</MenuItem>
                                    </Select>

                                    <Button size="small" variant="contained" onClick={() => handleSaveRole(user.id)} disabled={saving}>Save</Button>
                                    <Button size="small" onClick={handleCancelEdit} disabled={saving}>Cancel</Button>
                                </div>
                            ) : (
                                <div style={{ display: 'flex', alignItems: 'center', gap: '8px', marginTop: '5px' }}>
                                    <span>{ulogaDisplayMap[user.uloga]}</span>
                                    <Button size="small" variant="outlined" onClick={() => handleEditClick(user.id, user.uloga)}>Edit</Button>
                                </div>
                            )}
                            </div>
                            <Button
                                variant="contained"
                                color="error"
                                onClick={() => handleDelete(user.id)}
                                disabled={user.uloga === "VLASNIK"}
                                size="small"
                                style={{ marginTop: '8px' }}
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
