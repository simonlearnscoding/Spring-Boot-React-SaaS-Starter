import { useQuery, useQueryClient } from "@tanstack/react-query";
import {
  Button,
  Typography,
  List,
  ListItem,
  ListItemText,
  CircularProgress,
  Alert,
  Box,
} from "@mui/material";
import { useState } from "react";
import CreateUserModal from "./CreateUserModal";

const fetchUsers = async () => {
  const res = await fetch("http://localhost:8080/users");
  if (!res.ok) throw new Error("Failed to fetch");
  return res.json();
};

function App() {
  const queryClient = useQueryClient();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const { data, isLoading, error } = useQuery({
    queryKey: ["users"],
    queryFn: fetchUsers,
  });

  const handleCreateUser = async (user: { name: string; email: string }) => {
    await fetch("http://localhost:8080/users", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(user),
    });

    queryClient.invalidateQueries({ queryKey: ["users"] });
  };

  return (
    <div className="p-8">
      <Typography variant="h3" component="h1" className="mb-6">
        User List
      </Typography>

      {isLoading && (
        <Box display="flex" justifyContent="center" my={4}>
          <CircularProgress />
        </Box>
      )}

      {error && (
        <Alert severity="error" className="mb-4">
          Error: {error.message}
        </Alert>
      )}

      {data && (
        <List
          sx={{ width: "100%", maxWidth: 600, bgcolor: "background.paper" }}
        >
          {data.map((user: any) => (
            <ListItem key={user.id} divider>
              <ListItemText primary={user.name} secondary={user.email} />
            </ListItem>
          ))}
        </List>
      )}

      <div className="mt-6 flex gap-4">
        <Button
          variant="contained"
          disableElevation
          className="bg-blue-500 hover:bg-blue-700"
          onClick={() => setIsModalOpen(true)}
        >
          Add New User
        </Button>

        <Button
          variant="outlined"
          onClick={() => queryClient.invalidateQueries({ queryKey: ["users"] })}
        >
          Refresh Data
        </Button>
      </div>

      <CreateUserModal
        open={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onCreate={handleCreateUser}
      />
    </div>
  );
}

export default App;
