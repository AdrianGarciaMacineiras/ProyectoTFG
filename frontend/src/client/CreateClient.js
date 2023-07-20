import React, { useState } from 'react';
import "../network.css";
import VisGraph from 'react-vis-graph-wrapper';

const ClientForm = () => {
  const [clientData, setClientData] = useState({
    code: '',
    name: '',
    industry: '',
    country: ''
  });
  const [graph, setGraph] = useState(null);

  const options = {
    layout: {
        improvedLayout: true
    },
    nodes:{
      shape: "dot",
      scaling: {min:10,label:false}
    },
    edges: {
      color: "#000000",
      smooth: {
        enabled: true,
        type: "discrete",
        roundness: 0.5
      }
    },
    height: "800px",
    physics: {
      barnesHut: {
        gravitationalConstant: -11500,
        centralGravity: 0.5,
        springLength: 270,
        springConstant: 0.135,
        avoidOverlap: 0.02
      },
      minVelocity: 0.75
    },
    configure: {
      enabled: true,
      filter: 'physics, layout',
      showButton: true
   },
    interaction: {
      hover: true,
      hoverConnectedEdges: true,
      selectable: true,
      selectConnectedEdges: true
    }
  };
  
  const events = {
    select: function(event) {
      var { nodes, edges } = event;
    }
  };
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setClientData((prevState) => ({
      ...prevState,
      [name]: value
    }));
  };

  const createClient = () => {
    const form = {
      code: clientData.code,
      name: clientData.name,
      industry: clientData.industry,
      country: clientData.country
    };

    const requestBody = JSON.stringify(form);

    fetch("http://localhost:9080/client", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*"
      },
      body: requestBody,
    })
      .then(response => response.json())
      .then(response => {
        setGraph({
          nodes: [{ id: 1, label: response.name, title: JSON.stringify(response, '', 2) }],
          edges: []
        });
      });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    createClient();

    setClientData({
      code: '',
      name: '',
      industry: '',
      country: ''
    });
  };

  return (
    <>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="code">Code:</label>
          <input
            type="text"
            id="code"
            name="code"
            value={clientData.code}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="name">Name:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={clientData.name}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="industry">Industry:</label>
          <input
            type="text"
            id="industry"
            name="industry"
            value={clientData.industry}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="country">Country:</label>
          <input
            type="text"
            id="country"
            name="country"
            value={clientData.country}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit">Create Client</button>
      </form>

      {graph && (
        <VisGraph
          graph={graph}
          options={options}
          events={events}
          getNetwork={network => {
            // if you want access to vis.js network api you can set the state in a parent component using this property
          }}
        />
      )}
    </>
  );
};

export default ClientForm;
