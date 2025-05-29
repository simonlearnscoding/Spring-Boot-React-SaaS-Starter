{pkgs ? import <nixpkgs> {config = {allowUnfree = true;};}}:
pkgs.mkShell {
  buildInputs = [
    pkgs.docker
    pkgs.podman-compose
    pkgs.entr
    # pkgs.nodejs
    # pkgs.typescript
    # pkgs.nodePackages.typescript-language-server
    # pkgs.redis
  ];

  shellHook = ''

    # Start MongoDB if not running
    if ! curl -s http://localhost:54333/ >/dev/null; then
      echo "Starting PostGresql container..."
      podman-compose up -d

      # while ! curl -s http://localhost:54333/ >/dev/null; do
      #   sleep 1
      #   echo "Waiting for PostGresql to start..."
      # done
    else
      echo "PSQL is already running"
    fi

    # # Start Redis if not running
    # if ! redis-cli ping | grep -q PONG; then
    #   echo "Starting Redis server..."
    #   redis-server --daemonize yes > /dev/null 2>&1
    #   while ! redis-cli ping | grep -q PONG; do
    #     sleep 1
    #     echo "Waiting for Redis to start..."
    #   done
    # else
    #   echo "Redis is already running"
    # fi
  '';
}
